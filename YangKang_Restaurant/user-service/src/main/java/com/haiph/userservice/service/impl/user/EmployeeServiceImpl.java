package com.haiph.userservice.service.impl.user;

import com.haiph.common.formEmail.ActivePerson;
import com.haiph.userservice.entity.person.Employee;
import com.haiph.userservice.entity.person.Person;
import com.haiph.userservice.feignClient.MailController;
import com.haiph.common.email.SendMail;
import com.haiph.userservice.model.request.employee.EmployeeRequest;
import com.haiph.userservice.model.response.EmployeeResponse;
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
    @Autowired
    private MailController mailController;

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
        sendMail(request.getEmail());
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


    private String sendMail(String email) {

        Optional<Person> person = personRepository.findByEmail(email);

        if (person.isPresent()) {
            String confirm = ActivePerson.CONFIRM + person.get().getPersonCode();
            StringBuilder message = new StringBuilder();
            message.append(confirm);
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "\n" +
                    "<style>\n" +
                    "    html{\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        box-sizing: border-box;\n" +
                    "        margin: 0px;\n" +
                    "        padding: 0px;\n" +
                    "        width: 1200px;\n" +
                    "    }\n" +
                    "    body{\n" +
                    "    }\n" +
                    "    .heading{\n" +
                    "        padding: 2rem 0rem 1.5rem 10rem;\n" +
                    "        font-size: 22px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "    .content{\n" +
                    "        padding: 2rem 0rem 0rem 10rem ;\n" +
                    "        font-size: 18px;\n" +
                    "        background-color: rgba(255, 59, 9, 0.2); \n" +
                    "        font-family: Arial, Helvetica, sans-serif;\n" +
                    "        color: brown;\n" +
                    "    }\n" +
                    "   \n" +
                    "</style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ\" crossorigin=\"anonymous\">\n" +
                    "    <div class=\"heading\">" + ActivePerson.SUBJECT + "</div>\n" +
                    "    <div class=\"content\"><p>" + ActivePerson.MESSAGE + "</p>\n" +
                    "            <br> \n" +
                    "        <p>" + message + "</p>\n" +
                    "    </div>\n" +
                    "            \n" +
                    "</body>\n" +
                    "</html>";
            SendMail emailRequest = SendMail.build(person.get().getEmail(), ActivePerson.SUBJECT, html);
            mailController.sendMail(emailRequest);
            return "Send mail to person: " + person.get().getPersonCode() + " success";
        }else
            return "Send mail to person: " + person.get().getPersonCode() + " fail";
    }
}
