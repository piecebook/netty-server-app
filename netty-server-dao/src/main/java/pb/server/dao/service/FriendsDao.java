package pb.server.dao.service;

import org.apache.ibatis.annotations.Param;
import pb.server.dao.model.Friend;
import pb.server.dao.model.FriendShip;

import java.util.List;

/**
 * Created by piecebook on 2016/9/12.
 */
public interface FriendsDao {
    public long add(@Param("uid1") long uid1, @Param("uid2") long uid2);

    public void remove(@Param("uid1") long uid1, @Param("uid2") long uid2);

    public List<FriendShip> getFriendShip(long uid);

    public List<Friend> getFriends(long uid);
}
