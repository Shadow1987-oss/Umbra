package gg.umbra.notification;

import gg.umbra.value.ToggleSetting;

public class FriendNotificationSettings {
    public final ToggleSetting friendOnline;
    public final ToggleSetting partyInviteAccepted;
    public final ToggleSetting partyInvites;
    public final ToggleSetting chats;
    public final ToggleSetting friendRequests;
    public final ToggleSetting general = ToggleSetting.create(null, "Too many pings", true);

    public FriendNotificationSettings() {
        this.friendRequests = ToggleSetting.create(null, "Friend requests", true);
        this.chats = ToggleSetting.create(null, "Chats", true);
        this.friendOnline = ToggleSetting.create(null, "Friend online", true);
        this.partyInvites = ToggleSetting.create(null, "Party invites", true);
        this.partyInviteAccepted = ToggleSetting.create(null, "Party invite accepted", true);
    }
}
