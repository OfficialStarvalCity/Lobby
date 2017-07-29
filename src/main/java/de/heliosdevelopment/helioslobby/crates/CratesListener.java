package de.heliosdevelopment.helioslobby.crates;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Teeage on 29.07.2017.
 */
public class CratesListener implements Listener{

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ENDER_CHEST){

            }
        }
    }
}
