package com.example.bouda.studentmanager.config;

import com.example.bouda.studentmanager.model.Student;
import com.example.bouda.studentmanager.model.Spec;
import com.example.bouda.studentmanager.repository.StudentRepository;
import com.example.bouda.studentmanager.repository.SpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DatabaseSeeder {
    private final Random random = new Random();

    private String[] firstNames = {
        "Ali", "Amir", "Hamza", "Hassan", "Idris", "Kareem", "Malik", "Muhammad", "Nasir", "Omar", "Samir", "Zayn", "Akram",
        "Bilal", "Daniyal", "Farid", "Marwan", "Nazih", "Qasim", "Suhail", "Ahmed", "Aziz", "Faruq", "Hisham", "Makram",
        "Mahmud", "Mehmed", "Nabil", "Rafi", "Sultan", "Adil", "Anas", "Badr", "Habib", "Hadi", "Jibril", "Naji", "Nizar", 
        "Tariq", "Zaki", "Abdullah", "Ahmad", "Harun", "Jabari", "Jalal", "Khalid", "Mustafa", "Qadir", "Saif", "Usama", 
        "Anwar", "Ayman", "Ghassan", "Hakeem", "Hussein", "Jamal", "Rayyan", "Sami", "Shadi", "Ziyad", "Abdul", "Aqil", 
        "Barak", "Fawaz", "Haytham", "Imran", "Mikha'il", "Nahar", "Yusuf", "Ziya"
    };

    private String[] lastNames = {
        "Chebli", "Chishti", "Dabbagh", "Dabbas", "Dahdah", "Daivari", "Damji", "Dandachi", "Daou", "Darwish", "Dawoud",
        "Dehlavi", "Diab", "Didouche", "Al-Dimashqi", "Din", "Dudin", "Dziri", "Ebeid", "Eid", 
        "Elaraby", "Elharar", "Elmaleh", "Elyounoussi", "Al-Enezi", "Entezam", "Erakat", "Fadel", "Faez", "Faheem", 
        "Fahimi", "Fahmi", "Fahri", "Faizan", "Fakhoury", "Fakhr", "Fares", "Farhat", "Farhi", "Farid", "Farooq", 
        "Farooqui", "Farrugia", "Al-Farsi", "Al-Fasi", "Fasih", "El", "Fawzi", "Fayed", "Fazil", "Ferchichi", "Filali", 
        "Fitoussi", "Gaddafi", "Gamil", "Gazzah", "Gebran", "Ghafalah", "Ghafour", "Ghali", "Ghamd", "Ghanem", 
        "Ghannouchi", "Gharsallah", "Ghattas", "Ghaus", "Ghazali", "Ghazzawi", "Ghulam", "Girgis", "Habib", "Habibi", 
        "Habus", "Haddad", "Haddaoui", "Hadi", "Hadid", "Hadrami", "Hafeez", "Hagigat", "Hajj", "Hajji", "Hakimi", 
        "Halabi", "Halimi", "Hamadani", "Hamawi", "Hamdan", "Al", "Hamdi", "Hamdouchi", "Hamid", "Hamidi", "Hamidou", 
        "Hammam", "Hamoudi", "Handal", "Hani", "Hanna", "Hannachi", "Haqq", "Harb", "Hariri", "Harouni", "Al-Harthi", 
        "Hashem", "Hasnawi", "Hassa", "Hassan", "Hassim", "Hatem", "Hawass", "Hawi", "Hawsawi", "Hayek", 
        "Hayel", "Hazazi", "Al-Hazmi", "Hegazi", "Henein", "Hijazi", "Hikmat", "Hilal", "Hilaly", "Himsi", "Hindawi", 
        "Hobeika", "Hourani", "Howayek", "Hussein", "Husseini", "Ibn", "Ibn", "Ibrahim", "Iqbal", "Irfan", "Isa", 
        "Ishak", "Islambouli", "Ismail", "Ismat", "Issawi", "Itani", "Jabal", "Jaber", "Jabeur", "Jabir", 
        "Al-Jabiri", "Jabr", "Jahani", "Jahid", "Jalal", "Jalayer", "Jalili", "Jalili", "Jameel", "Jarrah", "Jarrar", 
        "Jasem", "Jazairi", "Al-Jazari", "Jaziri", "Jebali", "Juhani", "Al-Jurjani", "Kabha", "Kaddouri", "Kadir", 
        "Kadri", "Kaebi", "Kahveci", "Kanso", "Karaki", "Karawi", "Karbalaei", "Kardoust", "Kareem", "Karib", "Kashif", 
        "Kassab", "Kassar", "Kateb", "Kayyali", "Kazem", "Kazmi", "Kchouk", "Khadem", "Khaled", "Khalef", "Khalid", 
        "Khamis", "Khatibi", "Khattab", "Khayat", "Khofri"
    };

    @Bean
    public CommandLineRunner initDatabase(StudentRepository studentRepository, SpecRepository specRepository) {
        return args -> {
            // Clear existing data
            studentRepository.deleteAll();
            specRepository.deleteAll();

            Spec spec1 = new Spec("GL", 40);
            Spec spec2 = new Spec("SCI", 20);
            Spec spec3 = new Spec("SI", 40);
            Spec spec4 = new Spec("TI", 40);
            specRepository.saveAll(List.of(spec1, spec2, spec3, spec4));

            List<Student> students = new ArrayList<>();

            for (String firstName : firstNames) {
            for (int i = 0; i < 2; i++) { 
                    
                    Student student = new Student();

                    String studentNumber = String.format("%08d", random.nextInt(100000000));

                    student.setStudentNumber(studentNumber);
                    student.setFirstName(firstName);
                    student.setLastName(lastNames[random.nextInt(lastNames.length)]);
                    
                    student.setSemester1Avg(formatDecimal(10 + random.nextDouble() * 10));
                    student.setSemester2Avg(formatDecimal(10 + random.nextDouble() * 10));
                    student.setSemester3Avg(formatDecimal(10 + random.nextDouble() * 10));
                    student.setSemester4Avg(formatDecimal(10 + random.nextDouble() * 10));

                    students.add(student);
                }   
            }
            
            studentRepository.saveAll(students);
        };
    }

    private double formatDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}