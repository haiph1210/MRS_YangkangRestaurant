package com.haiph.menuservice;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<menu> menus = new ArrayList<>();
        System.out.println(menus.size());
    }
    public class menu{
        int stamen;

        public menu(int stamen) {
            this.stamen = stamen;
        }

        public int getStamen() {
            return stamen;
        }

        public void setStamen(int stamen) {
            this.stamen = stamen;
        }
    }
}
