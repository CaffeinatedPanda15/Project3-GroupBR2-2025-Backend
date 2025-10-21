package za.ac.cput.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Address;
import za.ac.cput.repositories.IAddressRepository;
import za.ac.cput.service.IAddressService;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address read(Integer id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address update(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }
}

//