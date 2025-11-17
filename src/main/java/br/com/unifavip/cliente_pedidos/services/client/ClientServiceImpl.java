package br.com.unifavip.cliente_pedidos.services.client;

import br.com.unifavip.cliente_pedidos.dto.client.input.ClientAddressUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.ClientInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.ClientUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.FindByFilterClientInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.output.ClientAddressOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.output.ClientOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.output.OrderItemOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.output.OrderOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductTypeOutputDTO;
import br.com.unifavip.cliente_pedidos.models.client.Client;
import br.com.unifavip.cliente_pedidos.models.client.ClientAddress;
import br.com.unifavip.cliente_pedidos.repository.client.ClientAddressRepository;
import br.com.unifavip.cliente_pedidos.repository.client.ClientRepository;
import br.com.unifavip.cliente_pedidos.specifications.client.ClientSpecification;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.unifavip.cliente_pedidos.response.client.ClientResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientAddressRepository clientAddressRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public CommonResponse<?> create(ClientInputDTO dto) {
        log.info("ClientServiceImpl Create client {}", dto);
        Client client = modelMapper.map(dto, Client.class);

        if (dto.getAddresses() != null && !dto.getAddresses().isEmpty()) {
            List<ClientAddress> addresses = dto.getAddresses().stream()
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

    @Transactional
    @Override
    public CommonResponse<?> update(ClientUpdateInputDTO dto) {
        log.info("ClientServiceImpl Update client {}", dto);

        Client existingClient = clientRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + dto.getId() + " não encontrado"));

        modelMapper.typeMap(ClientUpdateInputDTO.class, Client.class)
                .addMappings(mapper -> mapper.skip(Client::setAddresses));

        modelMapper.map(dto, existingClient);

        if (existingClient.getAddresses() == null) {
            existingClient.setAddresses(new ArrayList<>());
        }

        List<ClientAddress> existingAddresses = existingClient.getAddresses();
        existingAddresses.clear();

        if (dto.getAddresses() != null && !dto.getAddresses().isEmpty()) {
            for (ClientAddressUpdateInputDTO addressDTO : dto.getAddresses()) {
                ClientAddress address = modelMapper.map(addressDTO, ClientAddress.class);

                if (address.getId() != null) {
                    ClientAddress existing = clientAddressRepository.findById(address.getId()).orElse(null);
                    if (existing == null || !existing.getClient().getId().equals(existingClient.getId())) {
                        address.setId(null);
                    }
                }
                address.setClient(existingClient);
                existingAddresses.add(address);
            }
        }

        Client savedClient = clientRepository.save(existingClient);
        return updated(clientToOutputDTO(savedClient));
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
                ClientSpecification.nameLike(dto.getName()),
                ClientSpecification.statusEquals(dto.getStatus())
        );

        return clientRepository.findAll(specification, pageable);
    }

    @Override
    public CommonResponse<?> delete(Long id) {
        log.info("ClientServiceImpl delete: {}", id);
        try {
            Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found!"));

            client.setStatus(false);
            clientRepository.save(client);
            return ok(clientToOutputDTO(client));
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public CommonResponse<?> findById(Long id) {
        log.info("ClientServiceImpl findById: {}", id);
        try {
            Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found!"));

            return founded(clientToOutputDTO(client));
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    private ClientOutputDTO clientToOutputDTO(Client client) {
        log.info("ClientServiceImpl ClientToOutputDTO: {}", client.getCpf());
        modelMapper.typeMap(Client.class, ClientOutputDTO.class)
                .addMappings(mapper -> mapper.skip(ClientOutputDTO::setOrders));

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
