package gg.umbra.value;

import gg.umbra.Umbra;
import gg.umbra.friend.FriendEntry;
import gg.umbra.value.AbstractListValueSuggestionProvider;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class FriendNameSuggestionProvider
extends AbstractListValueSuggestionProvider {
    @Override
    public @UnmodifiableView List<String> getValues() {
        ArrayList<String> names = new ArrayList<String>();
        for (FriendEntry friend : Umbra.INSTANCE.getFriendManager().getFriends()) {
            names.add(friend.getDisplayName());
        }
        return names;
    }
}
