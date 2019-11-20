package me.driftay.score.listeners;

import me.driftay.score.utils.Message;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player dead = e.getEntity();

        EntityDamageEvent eD = dead.getLastDamageCause();

        if(eD instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
            Entity damager = event.getDamager();

            if(damager instanceof Player) { // Player
                Player player = (Player)damager;
                if(player.getItemInHand() != null) {
                    ItemStack weapon = player.getItemInHand();

                    e.setDeathMessage(null);


                } else { // Fists :)
                    e.setDeathMessage(Message.DM_PLAYER.getMessage().replace("%victim%", dead.getName())
                            .replace("%killer%", player.getName()));
                }


            } else if(damager instanceof Creature) { // Mobs
                if(damager instanceof Monster) {
                    if(damager instanceof Zombie) { // zombie
                        Zombie zombie = (Zombie) damager;
                        if(zombie.isBaby()) {
                            e.setDeathMessage(get(dead, Message.DM_ZOMBIE_BABY));
                        } else if(zombie.isVillager()) {
                            e.setDeathMessage(get(dead, Message.DM_ZOMBIE_VILLAGER));
                        } else if(!zombie.isVillager() && !zombie.isVillager()){
                            e.setDeathMessage(get(dead, Message.DM_ZOMBIE_NORMAL));
                        }

                    } else if(damager instanceof Skeleton) {
                        e.setDeathMessage(get(dead, Message.DM_SKELETON));
                    } else if(damager instanceof Enderman) {
                        e.setDeathMessage(get(dead, Message.DM_ENDERMAN));
                    } else if(damager instanceof Blaze) {
                        e.setDeathMessage(get(dead, Message.DM_BLAZE));
                    } else if(damager instanceof Witch) {
                        e.setDeathMessage(get(dead, Message.DM_WITCH));
                    } else if(damager instanceof CaveSpider) {
                        e.setDeathMessage(get(dead, Message.DM_CAVE_SPIDER));
                    } else if(damager instanceof Spider) {
                        e.setDeathMessage(get(dead, Message.DM_SPIDER));
                    } else if(damager instanceof Silverfish) {
                        e.setDeathMessage(get(dead, Message.DM_SILVERFISH));
                    } else if(damager instanceof Creeper) {
                        Creeper creeper = (Creeper) damager;
                        if(creeper.isPowered()) {
                            e.setDeathMessage(get(dead, Message.DM_CREEPER_POWERED));
                        } else {
                            e.setDeathMessage(get(dead, Message.DM_CREEPER_NORMAL));
                        }
                    } else if(damager instanceof Giant) {
                        e.setDeathMessage(get(dead, Message.DM_GIANT));
                    } else if (damager instanceof Guardian) {
                        Guardian guardian = (Guardian) damager;
                        if(guardian.isElder()) {
                            e.setDeathMessage(get(dead, Message.DM_GUARDIAN_ELDER));
                        } else {
                            e.setDeathMessage(get(dead, Message.DM_GUARDIAN_NORMAL));
                        }

                    } else {
                        // Other mobs
                        e.setDeathMessage(Message.DM_OTHER.getMessage().replace("%victim%", dead.getName())
                                .replace("%mob_name%", damager.getType().getName()
                                        .replaceAll("_", "")));
                    }
                }
            } else if(damager instanceof WitherSkull) { // Wither
                e.setDeathMessage(get(dead, Message.DM_WITHER));

            } else if(damager instanceof TNTPrimed) { // Explosion (tnt)
                e.setDeathMessage(get(dead, Message.DM_TNT));

            } else if(damager instanceof LargeFireball || damager instanceof SmallFireball) {
                e.setDeathMessage(get(dead, Message.DM_FIREBALL));

            } else if(damager instanceof ThrownPotion) {
                e.setDeathMessage(get(dead, Message.DM_THROWN_POTION));

            } else if(damager instanceof Projectile) { // Projectile
                Projectile projectile = (Projectile) damager;
                ProjectileSource source = projectile.getShooter();
                if(source instanceof Player) {
                    if(source instanceof EnderPearl) { // pearl

                    } else if(source instanceof Arrow) { // arrow

                    }
                }
            }

        } else {
            switch (eD.getCause()) {
                case FALL:
                    e.setDeathMessage(get(dead, Message.DM_FALL));
                case DROWNING:
                    e.setDeathMessage(get(dead, Message.DM_DROWNED));
                case SUFFOCATION:
                    e.setDeathMessage(get(dead, Message.DM_SUFFOCATION));
                case FIRE:
                    e.setDeathMessage(get(dead, Message.DM_FIRE));
                case LAVA:
                    e.setDeathMessage(get(dead, Message.DM_LAVA));
                case VOID:
                    e.setDeathMessage(get(dead, Message.DM_VOID));
                case STARVATION:
                    e.setDeathMessage(get(dead, Message.DM_STARVATION));
                case POISON:
                    e.setDeathMessage(get(dead, Message.DM_POISON));
                case MAGIC:
                    e.setDeathMessage(get(dead, Message.DM_MAGIC));
                case FALLING_BLOCK:
                    e.setDeathMessage(get(dead, Message.DM_FALLING_BLOCK));
                case SUICIDE:
                    e.setDeathMessage(get(dead, Message.DM_SUICIDE));
            }

        }

    }

    private String get(Player victim, Message message) {
        return message.getMessage().replace("%victim%", victim.getName());
    }

    private FancyMessage getMessageWeapon(ItemStack weapon, String victim, String killer) {
        String itemName;

        if(weapon == null || weapon.getType().equals(Material.AIR)) return null;

        if(weapon.hasItemMeta() && weapon.getItemMeta().getDisplayName() != null) {
            itemName = "§r§b[§r" + weapon.getItemMeta().getDisplayName() + "§r§b]§r";
        } else {
            itemName = "§r§b[§r" + weapon.getType().name().toLowerCase().replaceAll("_", " ") + "§r§b]§r";
        }

        FancyMessage fm = new FancyMessage();
        fm.text("");

        String[] s = Message.DM_PLAYER_WITH_WEAPON.getMessage()
                .replace("%victim%", victim)
                .replace("%killer%", killer)
                .split(" ");


        for(String str : s) {
            if(str.contains("%weapon%")) {
                fm.then(" " + itemName)
                        .tooltip(weapon.toString());
            } else {
                fm.then(" " + str);
            }
        }

        return fm;
    }


}
