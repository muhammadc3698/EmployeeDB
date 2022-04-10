package com.example.EmployeeDB;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import com.example.EmployeeDB.employee.Employee;
import com.example.EmployeeDB.employee.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeDBApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	//Addition Tests
	@Test
	void CheckingInititalAddition() 
	{
		Employee tempEmp = new Employee("Amanda", "Jones", "AmandaGJones@dayrep.com", 4346855861l, LocalDate.of(1983, Month.APRIL, 26), "fullstack developer","devops","hunt valley", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		employeeRepository.save(tempEmp);

		Boolean exists = employeeRepository.findByEmail(tempEmp.getEmail()).isPresent();
		assertTrue(exists);
	}

	@Test
	void CheckingDuplicateEmails() 
	{
		Employee tempEmp = new Employee("Amanda", "Jones", "AmandaGJones@dayrep.com", 4324855861l, LocalDate.of(1983, Month.APRIL, 26), "fullstack developer","devops","hunt valley", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		employeeRepository.save(tempEmp);

		Boolean exists = employeeRepository.findByEmail(tempEmp.getEmail()).isPresent();
		assertFalse(exists);
	}

	@Test
	void CheckingDuplicatePhoneNumber() 
	{
		Employee tempEmp = new Employee("Amanda", "Jones", "AmandaGJones212@dayrep.com", 4346855861l, LocalDate.of(1983, Month.APRIL, 26), "fullstack developer","devops","hunt valley", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		employeeRepository.save(tempEmp);

		Boolean exists = employeeRepository.findByEmail(tempEmp.getEmail()).isPresent();
		assertFalse(exists);
	}

	@Test
	void AddingNewEmployeeWithInvalidCity() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "fullstack developer","devops","Boston", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		employeeRepository.save(tempEmp);

		Boolean exists = employeeRepository.findByEmail(tempEmp.getEmail()).isPresent();
		assertFalse(exists);
	}

	@Test
	void AddingNewEmployeeWithInvalidDepartment() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "fullstack developer","AI","hunt valley", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		employeeRepository.save(tempEmp);

		Boolean exists = employeeRepository.findByEmail(tempEmp.getEmail()).isPresent();
		assertFalse(exists);
	}

	@Test
	void AddingNewEmployeeWithInvalidJobTitle() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "Marketing Manager","devops","hunt valley", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		employeeRepository.save(tempEmp);

		Boolean exists = employeeRepository.findByEmail(tempEmp.getEmail()).isPresent();
		assertFalse(exists);
	}
	
	@Test
	void AddingSecondEmployee() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "fullstack developer","backend","baltimore", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		employeeRepository.save(tempEmp);

		Boolean exists = employeeRepository.findById(2l).isPresent();
		assertTrue(exists);
	}

	//RetrieveTests
	@Test
	void RetrievingInfo() 
	{
		Employee emp = employeeRepository.findByEmail("JamesMiller@gmail.com").get();

		Boolean exists = (emp.getFirstName() == "James" && emp.getJobtitle() == "fullstack developer");
		assertTrue(exists);
	}

	//Update tests
	@Test
	void UpdateEmailWithExisting() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "fullstack developer","backend","baltimore", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		String newEmail = "AmandaGJones@dayrep.com";
		tempEmp.setEmail(newEmail);
		employeeRepository.save(tempEmp);


		Boolean exists = (employeeRepository.findById(2l).get().getEmail() == "AmandaGJones@dayrep.com");
		assertFalse(exists);
	}

	@Test
	void UpdatePhoneWithExisting() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "fullstack developer","backend","baltimore", LocalDate.of(2021, Month.MAY, 12), null, "George McLennan" );
		Long phone = 4346855861l;
		tempEmp.setPhone(phone);
		employeeRepository.save(tempEmp);


		Boolean exists = (employeeRepository.findById(2l).get().getPhone() == 4346855861l);
		assertFalse(exists);
	}

	@Test
	void UpdateWithEndDateBeforeStartDate() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "fullstack developer","backend","baltimore", LocalDate.of(2021, Month.MAY, 12), LocalDate.of(2020, Month.MAY, 12), "George McLennan" );
		employeeRepository.save(tempEmp);


		Boolean exists = (employeeRepository.findById(2l).get().getEndDate() == LocalDate.of(2020, Month.MAY, 12));
		assertFalse(exists);
	}

	@Test
	void UpdateWithEndDateAfterStartDate() 
	{
		Employee tempEmp = new Employee("James", "Miller", "JamesMiller@gmail.com", 8829103721l, LocalDate.of(1992, Month.JULY, 2), "fullstack developer","backend","baltimore", LocalDate.of(2021, Month.MAY, 12), LocalDate.of(2024, Month.MAY, 12), "George McLennan" );
		employeeRepository.save(tempEmp);


		Boolean exists = (employeeRepository.findByEmail("JamesMiller@gmail.com").get().getEndDate() == LocalDate.of(2024, Month.MAY, 12));
		assertTrue(exists);
	}


	//DeletionTests
	@Test
	void DeletingExistingEmployee() 
	{
		Employee tempEmp = employeeRepository.getById(1l);
		employeeRepository.delete(tempEmp);

		Boolean exists = employeeRepository.findById(1l).isPresent();
		assertFalse(exists);
	}
}
