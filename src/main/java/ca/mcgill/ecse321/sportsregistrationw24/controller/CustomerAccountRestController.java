package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CustomerAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.CustomerAccountService;
import ca.mcgill.ecse321.sportsregistrationw24.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CustomerAccountRestController {

    @Autowired
    private CustomerAccountService service;

    //DONT DO LIKE THIS NEED TO CHECK
    private final PasswordValidation passwordValidation = new PasswordValidation();

    @PostMapping(value = {"/customerAccounts/create}","/customerAccounts/create/"})
    public CustomerAccountDto createCustomerAccount(@RequestBody CustomerAccountDto customerAccountDto,
                                                    @RequestParam String passwordConfirmation) throws IllegalArgumentException{
        String email = customerAccountDto.getEmail();
        String password = customerAccountDto.getPassword();

        //DONT DO LIKE THIS NEED TO CHECK
        passwordValidation.validatePassword(password, passwordConfirmation);

        CustomerAccount customerAccount = service.createCustomerAccount(email, password, passwordConfirmation);
        return convertToDto(customerAccount);
    }

    @GetMapping(value = {"/customerAccounts/get", "/customerAccounts/get/"})
    public CustomerAccountDto getCustomerAccount(@RequestParam String email) {
        CustomerAccount customerAccount = service.getCustomerAccount(email);
        return convertToDto(customerAccount);
    }

    @GetMapping(value = {"/customerAccounts/getAll", "/customerAccounts/getAll/"})
    public List<CustomerAccountDto> getAllCustomerAccounts() {
        List<CustomerAccountDto> customerAccountDtos = new ArrayList<>();
        for (CustomerAccount customerAccount : service.getAllCustomerAccounts()) {
            customerAccountDtos.add(convertToDto(customerAccount));
        }
        return customerAccountDtos;
    }

    @DeleteMapping(value = {"/customerAccounts/delete", "/customerAccounts/delete/"})
    public void deleteCustomerAccount(@RequestParam String email) {
        service.deleteCustomerAccount(email);
    }

    private CustomerAccountDto convertToDto(CustomerAccount customerAccount) {
        if (customerAccount == null) {
            throw new IllegalArgumentException("There is no such customer account!");
        }
        return new CustomerAccountDto(customerAccount.getEmail(),
                customerAccount.getPassword());
    }

}
