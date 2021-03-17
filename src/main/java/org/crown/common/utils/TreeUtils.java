package org.crown.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.crown.project.system.menu.domain.Menu;

/**
 * Authorization data processing
 *
 * @author Crown
 */
public class TreeUtils {

    /**
     * Get all child nodes according to the ID of the parent node
     *
     * @param list     Classification table
     * @param parentId Incoming parent node ID
     * @return String
     */
    public static List<Menu> getChildPerms(List<Menu> list, int parentId) {
        List<Menu> returnList = new ArrayList<>();
        for (Menu t : list) {
            // 1. According to a parent node ID passed in, traverse all child nodes of the parent node
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * Recursive list
     *
     * @param list
     * @param t
     */
    private static void recursionFn(List<Menu> list, Menu t) {
        // Get a list of child nodes
        List<Menu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Menu tChild : childList) {
            if (hasChild(list, tChild)) {
                // Determine whether there are child nodes
                Iterator<Menu> it = childList.iterator();
                while (it.hasNext()) {
                    Menu n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * Get a list of child nodes
     */
    private static List<Menu> getChildList(List<Menu> list, Menu t) {

        List<Menu> tlist = new ArrayList<>();
        Iterator<Menu> it = list.iterator();
        while (it.hasNext()) {
            Menu n = it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    final List<Menu> returnList = new ArrayList<>();

    /**
     * Get all child nodes according to the ID of the parent node
     *
     * @param list   Classification table
     * @param typeId Incoming parent node ID
     * @param prefix Child node prefix
     */
    public List<Menu> getChildPerms(List<Menu> list, int typeId, String prefix) {
        if (list == null) {
            return null;
        }
        for (Menu node : list) {
            // 1. According to a parent node ID passed in, traverse all child nodes of the parent node
            if (node.getParentId() == typeId) {
                recursionFn(list, node, prefix);
            }
            // 2. Traverse all child nodes under all parent nodes
            /*
             * if (node.getParentId()==0) { recursionFn(list, node); }
             */
        }
        return returnList;
    }

    private void recursionFn(List<Menu> list, Menu node, String p) {
        // Get a list of child nodes
        List<Menu> childList = getChildList(list, node);
        if (hasChild(list, node)) {
            // Determine whether there are child nodes
            returnList.add(node);
            Iterator<Menu> it = childList.iterator();
            while (it.hasNext()) {
                Menu n = it.next();
                n.setMenuName(p + n.getMenuName());
                recursionFn(list, n, p + p);
            }
        } else {
            returnList.add(node);
        }
    }

    /**
     * Determine whether there are child nodes
     */
    private static boolean hasChild(List<Menu> list, Menu t) {
        return getChildList(list, t).size() > 0;
    }
}
