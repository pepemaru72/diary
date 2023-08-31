package diary.repository;

import java.util.Optional;

import diary.entity.User;

public interface IUserAccountDao {
	
	// Userを取得
    Optional<User> findUser(String userId);

}
