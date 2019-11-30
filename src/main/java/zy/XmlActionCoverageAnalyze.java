package zy;

import java.util.*;

/**
 * Created by kaiser_zhao on 2018/7/29.
 */
public class XmlActionCoverageAnalyze {

    private static Map<String, XmlCase> xmlCaseNameMap = new HashMap<String, XmlCase>();
    private static Map<String, Set<String>> xmlCaseActionsMap = new HashMap<String, Set<String>>();
    public final static void main(String... strings){

        Generator.generateMaps(xmlCaseNameMap, xmlCaseActionsMap);
        //getAllActionsByBC();
        //getAllActions();
        calculateMixSet();
    }

    private static void calculateMixSet(){
        Set<String> remainFile = new HashSet<String>();

        //printCoverage(xmlCaseNameMap.keySet().iterator());

        // get all of actions
        Set<String> allActions = getAllActions(xmlCaseActionsMap);
        System.out.println("all actions number :" + allActions.size());

        // loop while there is action uncovered
        System.out.println("case number :" + xmlCaseActionsMap.size());

        //printCoverage(allActions.iterator());

        while(!allActions.isEmpty()) {

            System.out.println("=============================");
            // get the max coverage of actions by xml file.
            int maxActionCoverage = xmlCaseActionsMap.entrySet().stream().max((a,b)->a.getValue().size()-b.getValue().size()).get().getValue().size();
            System.out.println("maxActionCoverage is: " + maxActionCoverage);

            // record it. and remove it from map
            String xmlFileMax = xmlCaseActionsMap.entrySet().stream().filter(x->x.getValue().size()==maxActionCoverage).findFirst().get().getKey();
            System.out.println("xml file with maxActionCoverage is: " + xmlFileMax);
            remainFile.add(xmlFileMax);

            // remove the actions covered by it from other xml files.
            Set<String> removedAction = new HashSet(xmlCaseActionsMap.get(xmlFileMax));

            // remove actions from all actions
            System.out.println("allActions size before remove: " + allActions.size());
            System.out.println("removedAction size: " + removedAction.size());

            allActions.removeAll(removedAction);
            System.out.println("allActions size after remove: " + allActions.size());

            xmlCaseActionsMap.entrySet().forEach(x->x.getValue().removeAll(removedAction));
            xmlCaseActionsMap.remove(xmlFileMax);

            // clear empty xml file
            Iterator<Map.Entry<String, Set<String>>> iter = xmlCaseActionsMap.entrySet().iterator();
            System.out.println("iter start " + xmlCaseActionsMap.size());
            while(iter.hasNext()){
                String key = iter.next().getKey();
                //System.out.println(key);
                if(xmlCaseActionsMap.get(key).size()==0){
                    iter.remove();
                }
            }
            System.out.println("iter end " + xmlCaseActionsMap.size());
            System.out.println("after clear");
            if(xmlCaseActionsMap.size()==0){
                break;
            }

        }
        List listActions = new LinkedList<String>(remainFile);
        Collections.sort(listActions);
        printCoverage(listActions.iterator());
        System.out.println(remainFile.size());
    }

    private static void getAllActionsByBC(){
        Set<String> actions = new HashSet<String>();
        xmlCaseNameMap.entrySet().stream().filter(x->x.getValue().getTag().contains("BROWSERCERT")).
                forEach(x->actions.addAll(xmlCaseActionsMap.get(x.getKey())));
        System.out.println("================ " + actions.size() + " actions in Browser Cert cases============");
        for(String action : actions){
            System.out.println(action);
        }
    }

    private static void getAllActions(){

        // get all of actions
        Set<String> allActions = getAllActions(xmlCaseActionsMap);

        // remove bc
        Set<String> actions = new HashSet<String>();

        //xmlCaseNameMap.entrySet().stream().filter(x->x.getValue().getTag().contains("BROWSERCERT")).
         //       forEach(x->actions.addAll(xmlCaseActionsMap.get(x.getKey())));

        //allActions.removeAll(actions);
        System.out.println("================ " + allActions.size() + " actions not in Browser Cert cases============");
        for(String action : allActions){
            System.out.println(action);
        }
    }

    private static Set<String> getAllActions(Map<String, Set<String>> maps){
        Set<String> allActions = new HashSet<String>();
        maps.entrySet().stream().forEach(x->allActions.addAll(x.getValue()));
        return allActions;
    }

    private static void printCoverage(Iterator<String> iter){
        iter.forEachRemaining(x->System.out.println(x));
    }
}
