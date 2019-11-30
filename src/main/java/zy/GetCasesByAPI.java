package zy;

import java.util.*;

/**
 * Created by kaiser_zhao on 25/09/2018.
 */
public class GetCasesByAPI {
    private static Map<String, XmlCase> xmlCaseNameMap = new HashMap<String, XmlCase>();
    private static Map<String, Set<String>> xmlCaseActionsMap = new HashMap<String, Set<String>>();
    public final static void main(String... strings){

        Generator.generateMaps(xmlCaseNameMap, xmlCaseActionsMap);

        String apis = "package1.class1.method1(java.util.HashMap)\n" +
                "package2.class2.method2(java.util.HashMap)\n" +
                "package3.class3.method3(java.util.HashMap)(java.util.HashMap)\n";

        String[] apisArray = apis.split("\n");
        Set<String> apiList = new HashSet<>();
        Arrays.stream(apisArray).forEach(x->{
            int start = x.indexOf("(");
            String onlyName = x.substring(0, start);
            int accessStart = onlyName.indexOf("packagestart.");
            String shortName = onlyName.substring(accessStart + "packagestart..".length());
            System.out.println(shortName);
            apiList.add(shortName);
        });

        Set<String> impactedCases = new TreeSet<>();
        int i = 0;
        for(String key : xmlCaseActionsMap.keySet()){
            Set<String> methods = xmlCaseActionsMap.get(key);

            for(String method : methods){
                if(apiList.contains(method)){
                    int index = key.indexOf("projectname");
                    //System.out.println("get api : " + key.substring(index));

                    //System.out.println("method is : " + method);
                    i++;
                    impactedCases.add(key.substring(index));
                    break;
                }
            }
        }
        System.out.println("size ===== " + i);

        for(String str : impactedCases){
            System.out.println(str);
        }
    }
}
