package com.rlax.lele.framework.codegen.model;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.rlax.lele.framework.codegen.dto.AppDTOGenerator;
import io.jboot.Jboot;
import io.jboot.codegen.CodeGenHelpler;
import io.jboot.codegen.model.JbootBaseModelGenerator;
import io.jboot.codegen.model.JbootModelnfoGenerator;

import javax.sql.DataSource;
import java.util.List;

/**
 * model代码自动生成
 * @author Rlax
 *
 */
public class AppModelGenerator {

    public static void doGenerate() {
        AppModelGeneratorConfig config = Jboot.config(AppModelGeneratorConfig.class);

        System.out.println(config.toString());

        if (StrKit.isBlank(config.getModelpackage())) {
            System.err.println("lele.ge.model.modelpackage 不可为空");
            System.exit(0);
        }

        if (StrKit.isBlank(config.getDtopackage())) {
            System.err.println("lele.ge.model.dtopackage 不可为空");
            System.exit(0);
        }

        String modelPackage = config.getModelpackage();
        String baseModelPackage = modelPackage + ".base";
        String dotPackage = config.getDtopackage();

        String modelDir = PathKit.getWebRootPath() + "/src/main/java/" + modelPackage.replace(".", "/");
        String baseModelDir = PathKit.getWebRootPath() + "/src/main/java/" + baseModelPackage.replace(".", "/");
        String dtoDir = PathKit.getWebRootPath() + "/src/main/java/" + dotPackage.replace(".", "/");

        System.out.println("start generate...");
        System.out.println("generate dir:" + modelDir);

        DataSource dataSource = CodeGenHelpler.getDatasource();

        AppMetaBuilder metaBuilder = new AppMetaBuilder(dataSource);

        if (StrKit.notBlank(config.getRemovedtablenameprefixes())) {
            metaBuilder.setRemovedTableNamePrefixes(config.getRemovedtablenameprefixes().split(","));
        }

        if (StrKit.notBlank(config.getExcludedtableprefixes())) {
            metaBuilder.setSkipPre(config.getExcludedtableprefixes().split(","));
        }

        List<TableMeta> tableMetaList = metaBuilder.build();
        CodeGenHelpler.excludeTables(tableMetaList, config.getExcludedtable());

        new JbootBaseModelGenerator(baseModelPackage, baseModelDir).generate(tableMetaList);
        new JbootModelnfoGenerator(modelPackage, baseModelPackage, modelDir).generate(tableMetaList);
        new AppDTOGenerator(dotPackage, dtoDir).generate(tableMetaList);

        System.out.println("entity generate finished !!!");

    }

}
