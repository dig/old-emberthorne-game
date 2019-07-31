package com.emberthorne.game.api.mobs.data;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;

import com.emberthorne.game.api.mobs.MobTiers;

public class NameUtil {
    public static int getBarLength(MobTiers tier) {
        if (tier == MobTiers.T1) {
            return 25;
        }
        if (tier == MobTiers.T2) {
            return 30;
        }
        if (tier == MobTiers.T3) {
            return 35;
        }
        if (tier == MobTiers.T4) {
            return 40;
        }
        if (tier == MobTiers.T5) {
            return 50;
        }
        return 25;
    }

    public static String generateOverheadBar(org.bukkit.entity.Entity ent, double cur_hp, double max_hp, MobTiers tier) {
        int max_bar = getBarLength(tier);
        
        ChatColor cc;

        DecimalFormat df = new DecimalFormat("##.#");
        double percent_hp = (double) (Math.round(100.0D * Double.parseDouble((df.format((cur_hp / max_hp)))))); // EX: 0.5054134131

        if (percent_hp <= 0 && cur_hp > 0) {
            percent_hp = 1;
        }

        cc = ChatColor.GREEN;

        double percent_interval = (100.0D / max_bar);
        int bar_count = 0;

        if (percent_hp <= 45) {
            cc = ChatColor.YELLOW;
        }
        if (percent_hp <= 20) {
            cc = ChatColor.RED;
        }

        String return_string = cc + ChatColor.BOLD.toString() + " " + ChatColor.RESET.toString() + cc.toString() + "";

        while (percent_hp > 0 && bar_count < max_bar) {
            percent_hp -= percent_interval;
            bar_count++;
            return_string += "|";
        }

        return_string += ChatColor.BLACK.toString();

        while (bar_count < max_bar) {
            return_string += "|";
            bar_count++;
        }

        return_string = return_string + cc + ChatColor.BOLD.toString();
        return return_string;
    }
}
