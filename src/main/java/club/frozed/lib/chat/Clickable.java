package club.frozed.lib.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

@NoArgsConstructor
@Getter
public class Clickable {

    private List<TextComponent> components = new ArrayList<>();
    private String hoverText;
    private String text;
    private Action action;

    public Clickable(String msg) {
        TextComponent message = new TextComponent(CC.translate(msg));

        this.components.add(message);
        this.text = msg;
    }

    public Clickable(String msg, String hoverMsg, String clickString, Action action) {
        this.add(msg, hoverMsg, clickString);
        this.text = msg;
        this.hoverText = hoverMsg;
        this.action = action;
    }

    public TextComponent add(String msg, String hoverMsg, String clickString) {
        TextComponent message = new TextComponent(CC.translate(msg));

        if (hoverMsg != null) {
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(CC.translate(hoverMsg)).create()));
        }

        if (clickString != null) {
            if (action != null){
                switch (action){
                    case URL:
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, clickString));
                        break;
                    case COMMAND:
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickString));
                        break;
                }
            } else {
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickString));
            }
        }

        this.components.add(message);
        this.text = msg;
        this.hoverText = hoverMsg;

        return message;
    }

    public void add(String message) {
        this.components.add(new TextComponent(message));
    }

    public void sendToPlayer(Player player) {
        player.spigot().sendMessage(this.asComponents());
    }

    public TextComponent[] asComponents() {
        return this.components.toArray(new TextComponent[0]);
    }

    public enum Action{
        URL,
        COMMAND
    }
}