package zy;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Created by kaiser_zhao on 2018/7/28.
 */
public class Generator {

    private static String xmlCaseDir = "/basedir";

    public static void generateMaps(Map<String, XmlCase> xmlCaseNameMap, Map<String, Set<String>> xmlCaseActionsMap){
        dealWithPath(xmlCaseDir, xmlCaseNameMap, xmlCaseActionsMap);

    }

    private static void dealWithPath(String path, Map<String, XmlCase> xmlCaseNameMap, Map<String, Set<String>> xmlCaseActionsMap){
        File file = new File(path);
        if(file.isDirectory()){
            File[] categoryFiles = file.listFiles();

            for (File catFile : categoryFiles) {

                String fullPath = catFile.getAbsolutePath();
                dealWithPath(fullPath,xmlCaseNameMap, xmlCaseActionsMap);
            }
        }else{
            if(path.contains("resiliencytest") || path.contains("upgrade/verify") || path.contains("upgrade/config")){
                return;
            }
            getXmlCase(path,xmlCaseNameMap, xmlCaseActionsMap);
        }
    }

    private static void getXmlCase(String filePath,Map<String, XmlCase> xmlCaseNameMap, Map<String, Set<String>> xmlCaseActionsMap) {

        //System.out.println(filePath);
        if (!filePath.endsWith(".xml")) {
            return;
        }

        Set<String> actions = UseCase.extractTestCases(filePath,xmlCaseNameMap);
        xmlCaseActionsMap.put(filePath,actions);
    }
}
