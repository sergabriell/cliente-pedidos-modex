package br.com.unifavip.cliente_pedidos.services.order;

import br.com.unifavip.cliente_pedidos.dto.enums.order.OrderStatus;
import br.com.unifavip.cliente_pedidos.dto.order.input.*;
import br.com.unifavip.cliente_pedidos.dto.order.output.OrderClientOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.output.OrderItemOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.output.OrderOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductTypeOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.UserOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.auth.AuthOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.group.UserGroupOutputDTO;
import br.com.unifavip.cliente_pedidos.models.client.Client;
import br.com.unifavip.cliente_pedidos.models.order.Order;
import br.com.unifavip.cliente_pedidos.models.order.OrderItem;
import br.com.unifavip.cliente_pedidos.models.product.Product;
import br.com.unifavip.cliente_pedidos.models.user.User;
import br.com.unifavip.cliente_pedidos.repository.client.ClientRepository;
import br.com.unifavip.cliente_pedidos.repository.order.OrderItemRepository;
import br.com.unifavip.cliente_pedidos.repository.order.OrderRepository;
import br.com.unifavip.cliente_pedidos.repository.product.ProductRepository;
import br.com.unifavip.cliente_pedidos.repository.user.UserRepository;
import br.com.unifavip.cliente_pedidos.specifications.order.OrderSpecification;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.unifavip.cliente_pedidos.response.order.OrderResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public CommonResponse<?> create(OrderInputDTO dto) {
        log.info("OrderServiceImpl Create order {}", dto);

        Order order = new Order();

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        AuthOutputDTO userLogged = (AuthOutputDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userLogged.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário criador não encontrado"));

        order.setClient(client);
        order.setCreatedBy(user);
        order.setObservation(dto.getObservation());
        order.setPaymentType(dto.getPaymentType());
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = new ArrayList<>();

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            items = dto.getItems().stream().map(itemDto -> {
                Product product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

                OrderItem orderItem = buildOrderItem(itemDto, product);
                orderItem.setOrder(order);
                return orderItem;
            }).collect(Collectors.toList());
        }

        order.setItems(items);
        order.setTotalPrice(calculateOrderTotalPrice(items));
        Order saved = orderRepository.save(order);
        return created(orderToOutputDTO(saved));
    }

    @Transactional
    @Override
    public CommonResponse<?> update(OrderUpdateInputDTO dto) {
        log.info("OrderServiceImpl Update order {}", dto);

        Order existing = orderRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        modelMapper.typeMap(OrderUpdateInputDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setItems));

        modelMapper.map(dto, existing);


        if (dto.getStatus().equals(OrderStatus.CANCELED)) {
            existing.getItems().forEach(orderItem -> {
                Product product = orderItem.getProduct();
                product.setQuantity(product.getQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            });
        } else {
            if (dto.getItems() != null && !dto.getItems().isEmpty()) {
                List<OrderItem> items = existing.getItems();
                items.clear();
                dto.getItems().forEach(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

                    OrderItem orderItem = buildOrderItem(itemDto, product, dto.getStatus());
                    orderItem.setOrder(existing);
                    items.add(orderItem);
                });
            }
        }
        existing.setTotalPrice(calculateOrderTotalPrice(existing.getItems()));
        Order saved = orderRepository.save(existing);
        return updated(orderToOutputDTO(saved));
    }

    @Override
    public CommonResponse<?> findById(Long id) {
        log.info("OrderServiceImpl findById: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        return founded(orderToOutputDTO(order));
    }

    @Override
    public Page<Order> findByFilter(FindByFilterOrderInputDTO dto, Pageable pageable) {
        Specification<Order> specification = Specification.allOf(
                OrderSpecification.idEquals(dto.getId()),
                OrderSpecification.clientIdEquals(dto.getClientId()),
                OrderSpecification.createdByUserIdEquals(dto.getCreatedByUserId()),
                OrderSpecification.statusEquals(dto.getStatus()),
                OrderSpecification.paymentTypeEquals(dto.getPaymentType()),
                OrderSpecification.productIdEquals(dto.getProductId()),
                OrderSpecification.productDescriptionLike(dto.getProductDescription()),
                OrderSpecification.priceAtPurchaseMin(dto.getTotalPriceMin()),
                OrderSpecification.priceAtPurchaseMax(dto.getTotalPriceMax())
        );
        return orderRepository.findAll(specification, pageable);
    }

    @Override
    public CommonResponse<?> findOrderByFilter(FindByFilterOrderInputDTO dto, Pageable pageable) {
        try {
            Page<Order> page = findByFilter(dto, pageable);
            PageImpl<OrderOutputDTO> out = new PageImpl<>(
                    page.getContent().stream()
                            .map(this::orderToOutputDTO)
                            .toList(),
                    pageable,
                    page.getTotalElements()
            );
            return founded(out);

        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Transactional
    @Override
    public CommonResponse<?> delete(Long id) {
        log.info("OrderServiceImpl delete {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        order.setStatus(OrderStatus.CANCELED);

        orderRepository.save(order);

        return ok(orderToOutputDTO(order));
    }

    private OrderItem buildOrderItem(OrderItemInputDTO dto, Product product) {
        if (product.getQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + product.getDescription());
        }

        product.setQuantity(product.getQuantity() - dto.getQuantity());
        productRepository.save(product);
        OrderItem item = new OrderItem();

        item.setProduct(product);
        item.setQuantity(dto.getQuantity());

        double price = product.getPrice() * dto.getQuantity();
        item.setPriceAtPurchase(price);
        return item;
    }

    private OrderItem buildOrderItem(OrderItemUpdateInputDTO dto, Product product, OrderStatus orderStatus) {

        OrderItem item;
        if (dto.getId() != null) {
            item = orderItemRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

            int oldQty = item.getQuantity();
            int newQty = dto.getQuantity();
            int diff = newQty - oldQty;

            if (!orderStatus.equals(OrderStatus.CANCELED)) {
                if (diff > 0) {
                    if (product.getQuantity() < diff) {
                        throw new RuntimeException("Estoque insuficiente para o produto: " + product.getDescription());
                    }
                    product.setQuantity(product.getQuantity() - diff);
                }

                if (diff < 0) {
                    product.setQuantity(product.getQuantity() + Math.abs(diff));
                }

                productRepository.save(product);
            }
            item.setProduct(product);
            item.setQuantity(newQty);
            item.setPriceAtPurchase(product.getPrice() * newQty);

            return item;
        }
        else {
            if (!orderStatus.equals(OrderStatus.CANCELED)) {

                if (product.getQuantity() < dto.getQuantity()) {
                    throw new RuntimeException("Estoque insuficiente para o produto: " + product.getDescription());
                }

                product.setQuantity(product.getQuantity() - dto.getQuantity());
                productRepository.save(product);
            }

            item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setPriceAtPurchase(product.getPrice() * dto.getQuantity());

            return item;
        }
    }

    private Double calculateOrderTotalPrice(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(OrderItem::getPriceAtPurchase)
                .sum();
    }

    private OrderOutputDTO orderToOutputDTO(Order order) {
        OrderOutputDTO dto = modelMapper.map(order, OrderOutputDTO.class);

        if (order.getItems() != null) {
            List<OrderItemOutputDTO> items = order.getItems().stream()
                    .map(item -> {
                        OrderItemOutputDTO out = modelMapper.map(item, OrderItemOutputDTO.class);

                        if (item.getProduct() != null) {
                            ProductOutputDTO prod = modelMapper.map(item.getProduct(), ProductOutputDTO.class);

                            if (item.getProduct().getProductType() != null) {
                                prod.setProductType(
                                        modelMapper.map(item.getProduct().getProductType(), ProductTypeOutputDTO.class)
                                );
                            }
                            out.setProduct(prod);
                        }
                        return out;
                    })
                    .toList();

            dto.setItems(items);
        }
        dto.getCreatedBy().setGroup(modelMapper.map(order.getCreatedBy().getUserGroup(), UserGroupOutputDTO.class));

        return dto;
    }
}
