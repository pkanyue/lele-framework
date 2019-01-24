package com.rlax.lele.framework.codegen.module;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.template.Engine;
import com.rlax.lele.framework.codegen.model.AppMetaBuilder;
import com.rlax.lele.framework.codegen.module.generator.BaseModelGenerator;
import com.rlax.lele.framework.codegen.module.generator.ModelGenerator;
import com.rlax.lele.framework.codegen.module.generator.ServiceApiGenerator;
import com.rlax.lele.framework.codegen.module.generator.ServiceProviderGenerator;
import io.jboot.app.JbootApplication;
import io.jboot.codegen.CodeGenHelpler;
import io.jboot.utils.StrUtil;

import javax.sql.DataSource;
import java.io.File;
import java.util.*;

/**
 * @author Michael Yang 杨福海 （fuhai999@gmail.com）
 * @version V1.0
 * @Title: 根据数据库信息，生成 maven module
 * @Package io.jpress.core.code
 */
public class ModuleGenerator {

    /** pom groupId */
    private String groupId;
    /** 项目maven父模块名称，例如：cp */
    private String parentShortName;
    /** 项目版本 */
    private String projectVersion;
    /** 包含开始表前缀，多个逗号分割 */
    private String includedtableprefixes;

    /** 是否RPC模式，RPC模式将会使用RPC注解标注 */
    private boolean ifRpc;
    /** 模块名称，如：jboot-admin-service-user */
    private String moduleName;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    /** 包含表，因为前面有表前缀条件，所以这块处理特殊情况，为全部表名 */
    private String dbTables;
    /**
     * 基础 base 包路径，根据该路径自动生成其他路径
     * basePackage.model
     * basePackage.service.api
     * basePackage.service.provider
     * basePackage.web
     */
    private String basePackage;
    /** 项目基础路径，代码将根据该路径生成 */
    private String basePath;

    public ModuleGenerator(String groupId, String parentShortName, String projectVersion, String includedtableprefixes, boolean ifRpc, String moduleName, String dbUrl, String dbUser, String dbPassword, String dbTables, String basePackage) {
        this.groupId = groupId;
        this.parentShortName = parentShortName;
        this.projectVersion = projectVersion;
        this.includedtableprefixes = includedtableprefixes;
        this.ifRpc = ifRpc;
        this.moduleName = moduleName;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbTables = dbTables;
        this.basePackage = basePackage;
        this.basePath = PathKit.getWebRootPath() + "/../" + parentShortName + "-" + moduleName;
    }

    public void gen() {
        genModule();
        genPomXml();
        genCode();
    }

    private void genModule() {
        String modelPath = basePath + "/" + parentShortName + "-" + moduleName + "-model";
        String webPath = basePath + "/" + parentShortName + "-" + moduleName + "-web";
        String serviceApiPath = basePath + "/" + parentShortName + "-" + moduleName + "-service-api";
        String serviceProviderPath = basePath + "/" + parentShortName + "-" + moduleName + "-service-provider";

        File modelFile = new File(modelPath);
        File webFile = new File(webPath);
        File serviceApiFile = new File(serviceApiPath);
        File serviceProviderFile = new File(serviceProviderPath);

        modelFile.mkdirs();
        webFile.mkdirs();
        serviceApiFile.mkdirs();
        serviceProviderFile.mkdirs();
    }

    private void genPomXml() {

        String modulePath = basePath;
        String modelPath = basePath + "/" + parentShortName + "-" + moduleName + "-model";
        String webPath = basePath + "/" + parentShortName + "-" + moduleName + "-web";
        String serviceApiPath = basePath + "/" + parentShortName + "-" + moduleName + "-service-api";
        String serviceProviderPath = basePath + "/" + parentShortName + "-" + moduleName + "-service-provider";


        File modelFile = new File(modelPath);
        File webFile = new File(webPath);
        File serviceApiFile = new File(serviceApiPath);
        File serviceProviderFile = new File(serviceProviderPath);

        makeSrcDirectory(modelFile);
        makeSrcDirectory(webFile);
        makeSrcDirectory(serviceApiFile);
        makeSrcDirectory(serviceProviderFile);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("moduleName", moduleName);
        map.put("groupId", groupId);
        map.put("parentShortName", parentShortName);
        map.put("projectVersion", projectVersion);
        map.put("ifRpc", ifRpc);
        Engine engine = new Engine();
        engine.setToClassPathSourceFactory();    // 从 class path 内读模板文件
        engine.addSharedMethod(new StrKit());

        File modulePom = new File(modulePath, "pom.xml");

        if (!modulePom.exists()) {
            engine.getTemplate("com/rlax/lele/framework/codegen/module/templates/pom_module_template.jf").render(map, new File(modulePath, "pom.xml"));
            engine.getTemplate("com/rlax/lele/framework/codegen/module/templates/pom_model_template.jf").render(map, new File(modelFile, "pom.xml"));
            engine.getTemplate("com/rlax/lele/framework/codegen/module/templates/pom_web_template.jf").render(map, new File(webFile, "pom.xml"));
            engine.getTemplate("com/rlax/lele/framework/codegen/module/templates/pom_service_api_template.jf").render(map, new File(serviceApiFile, "pom.xml"));
            engine.getTemplate("com/rlax/lele/framework/codegen/module/templates/pom_service_provider_template.jf").render(map, new File(serviceProviderFile, "pom.xml"));
        }
    }

    private void makeSrcDirectory(File file) {
        if (!file.isDirectory()) {
            return;
        }

        new File(file, "src/main/java").mkdirs();
        new File(file, "src/main/resources").mkdirs();
        new File(file, "src/test/java").mkdirs();
        new File(file, "src/test/resources").mkdirs();
    }


    private void genCode() {

        String modelModuleName = "/" + parentShortName + "-" + moduleName + "-model";
        String serviceApiModuleName = "/" + parentShortName + "-" + moduleName + "-service-api";
        String serviceProviderModuleName = "/" + parentShortName + "-" + moduleName + "-service-provider";
        String serviceWebModuleName = "/" + parentShortName + "-" + moduleName + "-web";

        JbootApplication.setBootArg("jboot.datasource.url", dbUrl);
        JbootApplication.setBootArg("jboot.datasource.user", dbUser);
        JbootApplication.setBootArg("jboot.datasource.password", dbPassword);

        String modelPackage = basePackage + ".model";
        String baseModelPackage = modelPackage + ".base";

        String modelDir = basePath + modelModuleName + "/src/main/java/" + modelPackage.replace(".", "/");
        String baseModelDir = basePath + modelModuleName + "/src/main/java/" + baseModelPackage.replace(".", "/");

        System.out.println("start generate... dir:" + modelDir);

        DataSource dataSource = CodeGenHelpler.getDatasource();
        AppMetaBuilder metaBuilder = new AppMetaBuilder(dataSource);
        List<TableMeta> tableMetaList = metaBuilder.build();

        if (StrUtil.isNotBlank(dbTables)) {
            List<TableMeta> newTableMetaList = new ArrayList<TableMeta>();

            Set<String> includeTableSet = StrUtil.splitToSet(dbTables, ",");
            Set<String> includeTablePreSet = StrUtil.splitToSet(includedtableprefixes, ",");
            for (TableMeta tableMeta : tableMetaList) {
                if (includeTableSet.contains(tableMeta.name.toLowerCase())) {
                    newTableMetaList.add(tableMeta);
                }

                // 表前缀
                for (String pre : includeTablePreSet) {
                    if (tableMeta.name.toLowerCase().startsWith(pre)) {
                        newTableMetaList.add(tableMeta);
                    }
                }
            }

            tableMetaList.clear();
            tableMetaList.addAll(newTableMetaList);
        }

        new BaseModelGenerator(baseModelPackage, baseModelDir).generate(tableMetaList);
        new ModelGenerator(modelPackage, baseModelPackage, modelDir).generate(tableMetaList);

        String servicePackage = basePackage + ".service.api";
        String apiPath = basePath + serviceApiModuleName + "/src/main/java/" + servicePackage.replaceAll("\\.", "/");
        String providerPackage = basePackage + ".service.provider";
        String providerPath = basePath + serviceProviderModuleName + "/src/main/java/" + providerPackage.replaceAll("\\.", "/");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ifRpc", ifRpc);
        new ServiceApiGenerator(servicePackage, modelPackage, apiPath).generate(tableMetaList);
        new ServiceProviderGenerator(servicePackage, providerPackage, modelPackage, providerPath, map).generate(tableMetaList);

        String webPackage = basePackage + ".service.provider";
        String webPath = basePath + serviceWebModuleName + "/src/main/java/" + basePackage.replaceAll("\\.", "/") + "/web";
        new File(webPath).mkdirs();
    }
}
