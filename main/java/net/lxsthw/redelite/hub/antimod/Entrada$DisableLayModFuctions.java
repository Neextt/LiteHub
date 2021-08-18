package net.lxsthw.redelite.hub.antimod;

enum Entrada$DisableLayModFuctions {
    POTIONS("POTIONS", 0, "POTIONS");

    private String name;

    private Entrada$DisableLayModFuctions(String s, int n, String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
