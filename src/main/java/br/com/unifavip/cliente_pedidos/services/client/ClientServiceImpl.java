package br.com.unifavip.cliente_pedidos.services.client;

import br.com.unifavip.cliente_pedidos.dto.client.input.ClientInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.FindByFilterClientInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.output.ClientAddressOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.output.ClientOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.output.OrderItemOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.output.OrderOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductTypeOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.UserOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.group.UserGroupOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.role.UserRolesOutputDTO;
import br.com.unifavip.cliente_pedidos.models.client.Client;
import br.com.unifavip.cliente_pedidos.models.client.ClientAddress;
import br.com.unifavip.cliente_pedidos.repository.client.ClientRepository;
import br.com.unifavip.cliente_pedidos.specifications.client.ClientSpecification;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.unifavip.cliente_pedidos.response.client.ClientResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public CommonResponse<?> create(ClientInputDTO clientInputDTO) {
        Client client = modelMapper.map(clientInputDTO, Client.class);

        if (clientInputDTO.getAddresses() != null && !clientInputDTO.getAddresses().isEmpty()) {
            List<ClientAddress> addresses = clientInputDTO.getAddresses().stream()
                    .map(addressDTO -> modelMapper.map(addressDTO, ClientAddress.class))
                    .collect(Collectors.toList());

            for (ClientAddress address : addresses) {
                address.setClient(client);
            }
            client.setAddresses(addresses);
        }
        Client savedClient = clientRepository.save(client);

        return created(modelMapper.map(savedClient, ClientOutputDTO.class));
    }

    @Override
    public CommonResponse<?> findClientByFilter(FindByFilterClientInputDTO dto, Pageable pageable) {
        log.info("ClientServiceImpl findClientByFilter: {}", dto);
        try {
            Page<Client> clientPage = findByFilter(dto, pageable);
            PageImpl<ClientOutputDTO> clientOutputDTOS = new PageImpl<>(
                    clientPage.stream()
                            .filter(Objects::nonNull)
                            .map(this::clientToOutputDTO)
                            .toList(), clientPage.getPageable(), clientPage.getTotalElements());

            return founded(clientOutputDTOS);
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public Page<Client> findByFilter(FindByFilterClientInputDTO dto, Pageable pageable) {
        log.info("ClientUserServiceImpl findByFilter: {}", dto);
        Specification<Client> specification = Specification.allOf(
                ClientSpecification.idEquals(dto.getId()),
                ClientSpecification.cpfLike(dto.getCpf()),
                ClientSpecification.nameLike(dto.getName())
        );

        return clientRepository.findAll(specification, pageable);
    }

    private ClientOutputDTO clientToOutputDTO(Client client) {
        log.info("ClientServiceImpl ClientToOutputDTO: {}", client.getCpf());

        ClientOutputDTO clientOutput = modelMapper.map(client, ClientOutputDTO.class);

        if (client.getAddresses() != null && !client.getAddresses().isEmpty()) {
            List<ClientAddressOutputDTO> addressDTOs = client.getAddresses().stream()
                    .map(address -> modelMapper.map(address, ClientAddressOutputDTO.class))
                    .collect(Collectors.toList());
            clientOutput.setAddresses(addressDTOs);
        }

        if (client.getOrders() != null && !client.getOrders().isEmpty()) {

            List<OrderOutputDTO> orderDTOs = client.getOrders().stream()
                    .map(order -> {
                        OrderOutputDTO orderDTO = modelMapper.map(order, OrderOutputDTO.class);

                        if (order.getItems() != null && !order.getItems().isEmpty()) {

                            List<OrderItemOutputDTO> itemDTOs = order.getItems().stream()
                                    .map(item -> {
                                        OrderItemOutputDTO itemDTO = modelMapper.map(item, OrderItemOutputDTO.class);

                                        if (item.getProduct() != null) {
                                            ProductOutputDTO productDTO = modelMapper.map(item.getProduct(), ProductOutputDTO.class);

                                            if (item.getProduct().getProductType() != null) {
                                                ProductTypeOutputDTO typeDTO = modelMapper.map(item.getProduct().getProductType(), ProductTypeOutputDTO.class);
                                                productDTO.setProductType(typeDTO);
                                            }

                                            itemDTO.setProduct(productDTO);
                                        }
                                        return itemDTO;
                                    })
                                    .collect(Collectors.toList());
                            orderDTO.setItems(itemDTOs);
                        }
                        return orderDTO;
                    })
                    .collect(Collectors.toList());
            clientOutput.setOrders(orderDTOs);
        }

        return clientOutput;
    }
}
