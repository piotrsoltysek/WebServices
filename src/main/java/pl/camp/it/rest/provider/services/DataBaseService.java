package pl.camp.it.rest.provider.services;

import org.springframework.stereotype.Service;
import pl.camp.it.rest.provider.model.Address;
import pl.camp.it.rest.provider.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataBaseService {

    private List<User> users = new ArrayList<>();

    public DataBaseService() {
        Address address1 = new Address();
        address1.setId(1);
        address1.setCity("Kraków");
        address1.setStreet("Ogrodowa");
        address1.setNo(5);

        User user1 = new User();
        user1.setId(1);
        user1.setLogin("mateusz");
        user1.setPass("mateusz");
        user1.setAddress(address1);

        Address address2 = new Address();
        address2.setId(2);
        address2.setCity("Wrocław");
        address2.setStreet("Fabryczna");
        address2.setNo(17);

        User user2 = new User();
        user2.setId(2);
        user2.setLogin("janusz");
        user2.setPass("janusz");
        user2.setAddress(address2);

        this.users.add(user1);
        this.users.add(user2);
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(int id) {
        Optional<User> user = users.stream().filter(x -> x.getId()==id).findFirst();

        return user.get();
    }

    public int addUserToDB(User user) {
        int newId = this.users.size() + 1;
        user.setId(newId);
        this.users.add(user);
        return newId;
    }

    public boolean updateUser(User user, int id) {
        Optional<User> userFromDb = users.stream().filter(x -> x.getId()==id).findFirst();
        if (userFromDb.isPresent()) {
            User userToUpdate = userFromDb.get();
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setLogin(user.getLogin());
            userToUpdate.setPass(user.getPass());
            return true;
        } else {
            return false;
        }
    }

    public void removeUser(int id) {
        Optional<User> userFromDb = users.stream().filter(x -> x.getId()==id).findFirst();
        if (userFromDb.isPresent()) {
            this.users.remove(userFromDb.get());
        }

    }
}
