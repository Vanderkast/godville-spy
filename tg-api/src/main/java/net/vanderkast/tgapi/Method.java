package net.vanderkast.tgapi;

public interface Method {
    default String getName() {
        var name = this.getClass().getSimpleName();
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}
