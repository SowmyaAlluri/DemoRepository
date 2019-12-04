package net.javaguide.springboot.springbootcrudrestfulwebservices.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguide.springboot.springbootcrudrestfulwebservices.exception.ResourceNotFoundException;
import net.javaguide.springboot.springbootcrudrestfulwebservices.model.Employee;
import net.javaguide.springboot.springbootcrudrestfulwebservices.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	 @Autowired
	 private EmployeeRepository employeeRepository;
	 
	 //create all employees api
	 @GetMapping("/employees")
	 public List<Employee> getAllEmployees(){
		 return employeeRepository.findAll();
	 }
	 
	 //create employee
	 @PostMapping("/employees")
	 public Employee createEmployee(@Valid @RequestBody Employee employee){
		 
		return employeeRepository.save(employee);	
		 
	 }
	 
	 //get employee by id
	 @GetMapping("employees/{id}")
	 public ResponseEntity<Employee>getemployeeById(@PathVariable(value = "id") long employeeId) 
			 throws ResourceNotFoundException{
		Employee employee =  employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee not found "));
		 return ResponseEntity.ok().body(employee);
	 }
	 @PutMapping("/employees/{id}")
	 public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long employeeId,@RequestBody Employee employeeDetails)
			 throws ResourceNotFoundException{
		 
			Employee employee =  employeeRepository.findById(employeeId)
					.orElseThrow(()->new ResourceNotFoundException("Employee not found "));
			employee.setFirstName(employeeDetails.getFirstName());
			employee.setLastName(employeeDetails.getLastName());
			employee.setEmailId(employeeDetails.getEmailId());
			employeeRepository.save(employee);
			return ResponseEntity.ok().body(employee);
	    
	 }
	//delete employee by id
	 @DeleteMapping("/employees/{id}")
	 public ResponseEntity deleteEmployee(@PathVariable(value="id") long employeeId)throws ResourceNotFoundException{
		  employeeRepository.findById(employeeId)
					.orElseThrow(()->new ResourceNotFoundException("Employee not found "));
		 employeeRepository.deleteById(employeeId);
		 return ResponseEntity.ok().build();
		 
	 }
}
