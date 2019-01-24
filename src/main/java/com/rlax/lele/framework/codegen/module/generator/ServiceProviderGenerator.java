/**
 * Copyright (c) 2015-2018, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rlax.lele.framework.codegen.module.generator;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.BaseModelGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ServiceProviderGenerator extends BaseModelGenerator {

    private final static String LELE_TEMPLATE = "com/rlax/lele/framework/codegen/module/templates/service_provider_template.jf";
    private final static String JBOOT_TEMPLATE = "io/jboot/codegen/service/service_impl_template.tp";

    private String providerPackage;
    private String modelPackage;
    private String servicePackage;
    private Map map;

    public ServiceProviderGenerator(String servicePackage, String providerPackage, String modelPackage, String providerPath, Map<String, Object> map) {
        super(providerPackage, providerPath);
        this.servicePackage = servicePackage;
        this.providerPackage = providerPackage;
        this.modelPackage = modelPackage;
        this.map = map;
        this.template = LELE_TEMPLATE;
    }

    @Override
    public void generate(List<TableMeta> tableMetas) {
        System.out.println("Generate base model ...");
        System.out.println("Base Model Output Dir: " + baseModelOutputDir);

        Engine engine = Engine.create("forServiceImpl");
        engine.setSourceFactory(new ClassPathSourceFactory());
        engine.addSharedMethod(new StrKit());
        engine.addSharedObject("getterTypeMap", getterTypeMap);
        engine.addSharedObject("javaKeyword", javaKeyword);

        for (TableMeta tableMeta : tableMetas) {
            genBaseModelContent(tableMeta);
        }
        writeToFile(tableMetas);
    }


    @Override
    protected void genBaseModelContent(TableMeta tableMeta) {
        Kv data = Kv.by("providerPackage", providerPackage);
        data.set("generateChainSetter", generateChainSetter);
        data.set("tableMeta", tableMeta);
        data.set("servicePackage", servicePackage);
        data.set("providerPackage", providerPackage);
        data.set("modelPackage", modelPackage);
        data.putAll(map);

        Engine engine = Engine.use("forServiceImpl");
        tableMeta.baseModelContent = engine.getTemplate(template).renderToString(data);
    }

    /**
     * base model 覆盖写入
     */
    @Override
    protected void writeToFile(TableMeta tableMeta) throws IOException {
        File dir = new File(baseModelOutputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String target = baseModelOutputDir + File.separator + tableMeta.modelName + "ServiceProvider" + ".java";

        File targetFile = new File(target);
        if (targetFile.exists()) {
            return;
        }


        FileWriter fw = new FileWriter(target);
        try {
            fw.write(tableMeta.baseModelContent);
        } finally {
            fw.close();
        }
    }
}
