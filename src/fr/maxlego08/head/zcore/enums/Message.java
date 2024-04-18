package fr.maxlego08.head.zcore.enums;

import fr.maxlego08.head.zcore.utils.nms.NMSUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Message {

    PREFIX("§8(§6zHead§8) "),

    TELEPORT_MOVE("§cYou must not move!"),
    TELEPORT_MESSAGE("§7Teleportation in §3%second% §7seconds!"),
    TELEPORT_ERROR("§cYou already have a teleportation in progress!"),
    TELEPORT_SUCCESS("§7Teleportation done!"),
    INVENTORY_CLONE_NULL("§cThe inventory clone is null!"),
    INVENTORY_OPEN_ERROR("§cAn error occurred with the opening of the inventory §6%id%§c."),
    TIME_DAY("%02d %day% %02d %hour% %02d %minute% %02d %second%"),
    TIME_HOUR("%02d %hour% %02d minute(s) %02d %second%"),
    TIME_MINUTE("%02d %minute% %02d %second%"),
    TIME_SECOND("%02d %second%"),

    FORMAT_SECOND("second"),
    FORMAT_SECONDS("seconds"),

    FORMAT_MINUTE("minute"),
    FORMAT_MINUTES("minutes"),

    FORMAT_HOUR("hour"),
    FORMAT_HOURS("hours"),

    FORMAT_DAY("d"),
    FORMAT_DAYS("days"),

    COMMAND_SYNTAXE_ERROR("§cYou must execute the command like this§7: §a%syntax%"),
    COMMAND_NO_PERMISSION("§cYou do not have permission to run this command."),
    COMMAND_NO_CONSOLE("§cOnly one player can execute this command."),
    COMMAND_NO_ARG("§cImpossible to find the command with its arguments."),
    COMMAND_SYNTAXE_HELP("§f%syntax% §7» §7%description%"),

    RELOAD("§aYou have just reloaded the configuration files."),

    DESCRIPTION_RELOAD("Reload configuration files"),
    DESCRIPTION_VERSION("Show plugin version"),
    DESCRIPTION_GIVE("Give a head"),
    DESCRIPTION_SEARCH("Search heads"),
    DESCRIPTION_HELP("Show commands"),
    REFRESH("§aThe data is being updated, please wait."),

    GIVE("§aYou just got the head §f%name% §8(§7%id%§8)"),
    NOT_FOUND("§cCannot find head with id§8: §f%id%"),

    INFO_MESSAGES(
            "&fCategory&8: &7%category%",
            "&fTags&8: &7%tags%",
            "&fID&8: &7%id%",
            "&fWith zMenu&8: &7material: zhd:%id%"
    ),
    INFO_COPY("&bClick here to copy the base64 code"),
    INFO_HOVER("&7Click here to copy the base64 code"),

    DESCRIPTION_INFO("Get head informations"),
    DESCRIPTION_SAVE("Save head informations"),
    DESCRIPTION_RANDOM("Get a random head"),

    INFO_ERROR_AIR("§cNo, the air has no information."),
    INFO_ERROR_NOT("§cThis item is not a zHead head."),
    INFO_ERROR_HEAD("§cHead with id §f%id% §cwas not found."),

    SAVE_ERROR_NAME("§cThe name already exists for this item, please select another one."),
    SAVE_SUCCESS("§aYou just saved the item §f%name%§a."),

    OPEN_NO_PERMISSION("§cYou do not have permission to open this category"),

    GIVE_HEAD("§aYou just got the player’s head §f%name%§a."),

    ;

    private List<String> messages;
    private String message;
    private Map<String, Object> titles = new HashMap<>();
    private boolean use = true;
    private MessageType type = MessageType.TCHAT;

    private ItemStack itemStack;

    /**
     * @param message
     */
    private Message(String message) {
        this.message = message;
        this.use = true;
    }

    /**
     * @param title
     * @param subTitle
     * @param a
     * @param b
     * @param c
     */
    private Message(String title, String subTitle, int a, int b, int c) {
        this.use = true;
        this.titles.put("title", title);
        this.titles.put("subtitle", subTitle);
        this.titles.put("start", a);
        this.titles.put("time", b);
        this.titles.put("end", c);
        this.titles.put("isUse", true);
        this.type = MessageType.TITLE;
    }

    /**
     * @param message
     */
    private Message(String... message) {
        this.messages = Arrays.asList(message);
        this.use = true;
    }

    /**
     * @param message
     */
    private Message(MessageType type, String... message) {
        this.messages = Arrays.asList(message);
        this.use = true;
        this.type = type;
    }

    /**
     * @param message
     */
    private Message(MessageType type, String message) {
        this.message = message;
        this.use = true;
        this.type = type;
    }

    /**
     * @param message
     * @param use
     */
    private Message(String message, boolean use) {
        this.message = message;
        this.use = use;
    }

    public String getMessage() {
        return message;
    }

    public String toMsg() {
        return message;
    }

    public String msg() {
        return message;
    }

    public boolean isUse() {
        return use;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return messages == null ? Arrays.asList(message) : messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isMessage() {
        return messages != null && messages.size() > 1;
    }

    public String getTitle() {
        return (String) titles.get("title");
    }

    public Map<String, Object> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, Object> titles) {
        this.titles = titles;
        this.type = MessageType.TITLE;
    }

    public String getSubTitle() {
        return (String) titles.get("subtitle");
    }

    public boolean isTitle() {
        return titles.containsKey("title");
    }

    public int getStart() {
        return ((Number) titles.get("start")).intValue();
    }

    public int getEnd() {
        return ((Number) titles.get("end")).intValue();
    }

    public int getTime() {
        return ((Number) titles.get("time")).intValue();
    }

    public boolean isUseTitle() {
        return (boolean) titles.getOrDefault("isUse", "true");
    }

    public String replace(String a, String b) {
        return message.replace(a, b);
    }

    public MessageType getType() {
        return type.equals(MessageType.ACTION) && NMSUtils.isVeryOldVersion() ? MessageType.TCHAT : type;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}

