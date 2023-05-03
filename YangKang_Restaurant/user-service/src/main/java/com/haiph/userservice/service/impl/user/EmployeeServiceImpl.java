package com.haiph.userservice.service.impl.user;

import com.haiph.userservice.entity.person.Employee;
import com.haiph.userservice.entity.person.Person;
import com.haiph.userservice.model.request.employee.EmployeeRequest;
import com.haiph.userservice.model.response.dto.EmployeeResponse;
import com.haiph.userservice.repository.person.EmployeeRepository;
import com.haiph.userservice.repository.person.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class EmployeeServiceImpl implements com.haiph.userservice.service.EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<EmployeeResponse> findAllPage(Pageable pageable) {
        Page<Employee> page = employeeRepository.findAll(pageable);
        List<Employee> employees = page.getContent();
        List<EmployeeResponse> dtos = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponse dto = new EmployeeResponse();
            if (employee!= null) {
                dto.setPersonCode(employee.getPersonCode());
                dto.setEmail(employee.getEmail());
                dto.setPhoneNumber(employee.getPhoneNumber());
                dto.setAddress(employee.getAddress());
                dto.setStatus(employee.getStatus());
                dto.setCmnd(employee.getCmnd());
                dto.setGender(employee.getGender());
                dto.setFullName(employee.getFullName());
                dto.setRole(employee.getRole());
                dto.setCreatedDate(employee.getCreatedDate());
                dto.setPosition(employee.getPosition());
                dto.setSalary(employee.getSalary());
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos,pageable, page.getTotalElements());
    }

    @Override
    public EmployeeResponse findById(UUID id) {
       Employee employee = employeeRepository.findById(id).orElse(null);

        EmployeeResponse dto = new EmployeeResponse();
        if (employee!= null) {
            dto.setPersonCode(employee.getPersonCode());
            dto.setEmail(employee.getEmail());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setAddress(employee.getAddress());
            dto.setGender(employee.getGender());
            dto.setFullName(employee.getFullName());
            dto.setRole(employee.getRole());
            dto.setCreatedDate(employee.getCreatedDate());
            dto.setPosition(employee.getPosition());
            dto.setSalary(employee.getSalary());
            dto.setStatus(employee.getStatus());
            dto.setCmnd(employee.getCmnd());
        }
        return dto;
    }

    public EmployeeResponse findByPersonCode(String personCode) {
        Employee employee = employeeRepository.findByPersonCode(personCode).orElse(null);

        EmployeeResponse dto = new EmployeeResponse();
        if (employee!= null) {
            dto.setPersonCode(employee.getPersonCode());
            dto.setEmail(employee.getEmail());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setAddress(employee.getAddress());
            dto.setGender(employee.getGender());
            dto.setFullName(employee.getFullName());
            dto.setRole(employee.getRole());
            dto.setCreatedDate(employee.getCreatedDate());
            dto.setPosition(employee.getPosition());
            dto.setStatus(employee.getStatus());
            dto.setCmnd(employee.getCmnd());
            dto.setSalary(employee.getSalary());
        }
        return dto;
    }

    @Override
    public String create(EmployeeRequest request) {
        Employee employee = mapper.map(request, Employee.class);
        String fullName = request.getFirstName() + " " + request.getLastName();
        employee.setPersonCode(generatePersonCode(fullName));
        employee.setCmnd(request.getCmnd());
        employeeRepository.save(employee);
        return "Create Success";
    }

    @Override
    public String update(UUID id ,EmployeeRequest request) {
        EmployeeResponse employeeResponse = findById(id);
        if (employeeResponse != null) {
            Employee employee = mapper.map(request, Employee.class);
            String fullName = request.getFirstName() + " " + request.getLastName();
            employee.setPersonCode(generatePersonCode(fullName));
            employeeRepository.save(employee);
            return "update Success";
        }
        return "update fail";

    }

    @Override
    public String deleteById(UUID id) {
        EmployeeResponse employeeResponse = findById(id);
        if (employeeResponse != null) {
            employeeRepository.deleteById(id);
            return "delete Success";
        }
        return "delete fail";

    }

    private static final int MAX_ATTEMPTS = 100;

    private String generatePersonCode(String fullName) {
        String temp = Normalizer.normalize(fullName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String output = pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
        String[] newFullName = output.split(" ");
        String newPersonCode = "";
        int number = 1;
        if (newFullName.length > 0) {
            String lastName = newFullName[newFullName.length - 1].toUpperCase();
            String firstName = newFullName[0].substring(0, 2).toUpperCase();
            newPersonCode = lastName + firstName + "-";
            int attempts = 0;
            while (attempts < MAX_ATTEMPTS) {
                Optional<Person> person = personRepository.findByPersonCode(newPersonCode + number);
                if (!person.isPresent()) {
                    newPersonCode += number;
                    break;
                }
                number++;
                attempts++;
            }
        }
        return newPersonCode;
    }
}
