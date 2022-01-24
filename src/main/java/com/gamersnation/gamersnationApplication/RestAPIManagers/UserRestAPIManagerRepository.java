package com.gamersnation.gamersnationApplication.RestAPIManagers;

import com.gamersnation.gamersnationApplication.player.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Repository
@Transactional(readOnly = true)
public interface UserRestAPIManagerRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT summonerName FROM AppUser")
    AppUser findUserByUserName(String userName);

    @Query(value = "SELECT summonerName FROM AppUser")
    String findUserNameByUserName(String userName);

    @Query("select password from AppUser where upper(summonerName) = upper(?1)")
    String findPasswordByUserName(String summonerName);
}
