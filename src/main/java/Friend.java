import java.util.ArrayList;

public class Friend implements Component {

    private ArrayList<Friend> friends;
    private boolean needToVisualizeFriends = true;

    Friend() {
        friends = new ArrayList<>();
    }

    @Override
    public void draw(int spaces) {
        for (int i = 0; i < spaces; i++) {
            System.out.print('-');
        }
        if (friends == null || friends.isEmpty()) {
            System.out.print("0");
        } else {
            System.out.print('+');
        }
        System.out.println();

        if (needToVisualizeFriends) {
            for (Friend friend : friends) {
                friend.draw(spaces + 2);
            }
        }
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public void setNeedToVisualizeFriends(boolean needToVisualizeFriends) {
        this.needToVisualizeFriends = needToVisualizeFriends;
    }
}
