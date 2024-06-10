import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


/*
 * This class includes test cases for the basic/normal functionality of the 
 * FriendFinder.findClassmates method, but does not check for any error handling.
 */

public class FindClassmatesTest {
	
	protected FriendFinder ff;
		
	protected ClassesDataSource defaultClassesDataSource = new ClassesDataSource() {

		@Override
		public List<String> getClasses(String studentName) {

			if (studentName.equals("A")) {
				return List.of("1", "2", "3");
			}
			else if (studentName.equals("B")) {
				return List.of("1", "2", "3");
			}
			else if (studentName.equals("C")) {
				return List.of("2", "4");
			}
			else return null;			
		
		}
		
	};
	
	protected StudentsDataSource defaultStudentsDataSource = new StudentsDataSource() {

		@Override
		public List<Student> getStudents(String className) {
			
			Student a = new Student("A", 101);
			Student b = new Student("B", 102);
			Student c = new Student("C", 103);

			if (className.equals("1")) {
				return List.of(a, b);
			}
			else if (className.equals("2")) {
				return List.of(a, b, c);
			}
			else if (className.equals("3")) {
				return List.of(a, b);
			}
			else if (className.equals("4")) {
				return List.of(c);
			}
			else return null;
		}
		
	};
	

	@Test
	public void testFindOneFriend() { 
		
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(new Student("A", 101));
		assertNotNull(response);
		assertEquals(1, response.size());
		assertTrue(response.contains("B"));

	}

	@Test
	public void testFindNoFriends() { 
		
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(new Student("C", 103));
		assertNotNull(response);
		assertTrue(response.isEmpty());

	}
	
	@Test
	public void testClassesDataSourceReturnsNullForInputStudent() { 
		
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(new Student("D", 104));
		assertNotNull(response);
		assertTrue(response.isEmpty());

	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullStudent() {
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(null);
	}

	@Test(expected= IllegalStateException.class)
	public void testNullStudentName() {
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(new Student(null, 103));
	}

	@Test
	public void testNullMyClass() {
		ClassesDataSource cds = new ClassesDataSource() {
			@Override
			public List<String> getClasses(String studentName) {
				if (studentName.equals("A")) {
					return Arrays.asList("1", "2", null);
				}
				else if (studentName.equals("B")) {
					return Arrays.asList("1", "2", "3");
				}
				else if (studentName.equals("C")) {
					return Arrays.asList("2", "4");
				}
				else return null;
			}
		};

		StudentsDataSource sds = new StudentsDataSource() {
			@Override
			public List<Student> getStudents(String className) {
				Student a = new Student("A", 101);
				Student b = new Student("B", 102);
				Student c = new Student("C", 103);
				if (className.equals("1")) {
					return List.of(a, b);
				}
				else if (className.equals("2")) {
					return List.of(a, b, c);
				}
				else if (className.equals("3")) {
					return List.of(b);
				}
				else if (className.equals("4")) {
					return List.of(c);
				}
				else if (className.equals(null)) {
					return List.of(a);
				}
				else return null;
			}
		};
		ff = new FriendFinder(cds, sds);
		Set<String> response = ff.findClassmates(new Student("A", 101));
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}


	@Test
	public void testNullStudents() {
		ClassesDataSource cds = new ClassesDataSource() {
			@Override
			public List<String> getClasses(String studentName) {
				if (studentName.equals("A")) {
					return Arrays.asList("1", "2", "5");
				}
				else if (studentName.equals("B")) {
					return Arrays.asList("1", "2", "3");
				}
				else if (studentName.equals("C")) {
					return Arrays.asList("2", "4");
				}
				else return null;
			}
		};

		StudentsDataSource sds = new StudentsDataSource() {
			@Override
			public List<Student> getStudents(String className) {
				Student a = new Student("A", 101);
				Student b = new Student("B", 102);
				Student c = new Student("C", 103);
				if (className.equals("1")) {
					return Arrays.asList(a, b);
				}
				else if (className.equals("2")) {
					return Arrays.asList(a, b, c);
				}
				else if (className.equals("3")) {
					return Arrays.asList(b);
				}
				else if (className.equals("4")) {
					return Arrays.asList(c);
				}
				else if (className.equals("5")) {
					return null;
				}
				else
					return null;
			}
		};
		ff = new FriendFinder(cds, sds);
		Set<String> response = ff.findClassmates(new Student("A", 101));
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

	@Test
	public void testNullOtherStudent() {
		ClassesDataSource cdsOtherStudent = new ClassesDataSource() {
			@Override
			public List<String> getClasses(String studentName) {
				if (studentName.equals("A")) {
					return Arrays.asList("1", "2", "5");
				}
				else if (studentName.equals("B")) {
					return Arrays.asList("1", "2", "3");
				}
				else if (studentName.equals("C")) {
					return Arrays.asList("2", "4");
				}
				else return null;
			}
		};

		StudentsDataSource sdsOtherStudent = new StudentsDataSource() {
			@Override
			public List<Student> getStudents(String className) {
				Student a = new Student("A", 101);
				Student b = new Student("B", 102);
				Student c = new Student("C", 103);
				if (className.equals("1")) {
					return Arrays.asList(a, b);
				}
				else if (className.equals("2")) {
					return Arrays.asList(a, b, c);
				}
				else if (className.equals("3")) {
					return Arrays.asList(b);
				}
				else if (className.equals("4")) {
					return Arrays.asList(c);
				}
				else if (className.equals("5")) {
					return Arrays.asList(a, null);
				}
				else
					return null;
			}
		};
		ff = new FriendFinder(cdsOtherStudent, sdsOtherStudent);
		Set<String> response = ff.findClassmates(new Student("A", 101));
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

	@Test
	public void testNullTheirClasses() {
		ClassesDataSource cdsOtherClasses = new ClassesDataSource() {
			@Override
			public List<String> getClasses(String studentName) {
				if (studentName.equals("A")) {
					return Arrays.asList("1", "2", "5");
				}
				else if (studentName.equals("B")) {
					return Arrays.asList("1", "2", "3");
				}
				else if (studentName.equals("C")) {
					return Arrays.asList("2", "4");
				}
				else if (studentName.equals("D")) {
					return null;
				}
				else return null;
			}
		};

		StudentsDataSource sdsOtherClasses = new StudentsDataSource() {
			@Override
			public List<Student> getStudents(String className) {
				Student a = new Student("A", 101);
				Student b = new Student("B", 102);
				Student c = new Student("C", 103);
				Student d = new Student("D", 103);
				if (className.equals("1")) {
					return Arrays.asList(a, b);
				}
				else if (className.equals("2")) {
					return Arrays.asList(a, b, c);
				}
				else if (className.equals("3")) {
					return Arrays.asList(b);
				}
				else if (className.equals("4")) {
					return Arrays.asList(c);
				}
				else if (className.equals("5")) {
					return Arrays.asList(a, d);
				}
				else
					return null;
			}
		};
		ff = new FriendFinder(cdsOtherClasses, sdsOtherClasses);
		Set<String> response = ff.findClassmates(new Student("A", 101));
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}



}
