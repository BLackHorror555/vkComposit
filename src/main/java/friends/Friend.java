package friends;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Friend implements Component {

    List<Friend> friends;
    private int id;

    Friend(int id) {
        friends = Collections.emptyList();
        this.id = id;
    }

    @Override
    public void draw(Graphics graphics, Point point) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
