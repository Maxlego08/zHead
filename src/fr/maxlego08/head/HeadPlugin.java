package fr.maxlego08.head;

import fr.maxlego08.head.placeholder.LocalPlaceholder;
import fr.maxlego08.head.save.MessageLoader;
import fr.maxlego08.head.zcore.ZPlugin;
import fr.maxlego08.head.command.commands.CommandTemplate;
import fr.maxlego08.head.save.Config;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class HeadPlugin extends ZPlugin {

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("template");

        this.preEnable();

        this.registerCommand("template", new CommandTemplate(this));

        this.addSave(Config.getInstance());
        this.addSave(new MessageLoader(this));

        this.loadFiles();

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

}
