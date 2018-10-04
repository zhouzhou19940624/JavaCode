package com.easycore.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class AutoCoder {
	public static void main(String[] args) {
		// 验证配置
		if (!isConfOK())
			return;
		// 开始生成
		try {
			// 设置工程src路径
			setSrcPath();
			// 生成各层目录
			makedirs();
			// 备份mapper接口文件
			backupMapper();
			// 生成持久层代码
			generate();
			// 还原mapper接口文件
			recoverMapper();
			// 生成controller/service代码
			genCtrlAndSrvc();
			// 输出信息
			System.out.println("-成功生成controller/service/dao/entity/model-");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 验证配置
	private static boolean isConfOK() {
		// 验证配置
		try {
			String persist_entity = ConfigUtils.getVal("config/core.properties", "persist.entity");
			String persist_dao = ConfigUtils.getVal("config/core.properties", "persist.dao");
			String[] arr_e = persist_entity.split("\\.");
			String[] arr_d = persist_dao.split("\\.");
			// persist_entity
			if (!arr_e[0].trim().equals("com") || !arr_e[1].trim().equals("easywork")
					|| !arr_e[arr_e.length - 1].trim().equals("entity")) {
				printerr();
				return false;
			}
			for (String str : arr_e) {
				if (!VerifyUtils.isLowerStr(str) || str.contains(" ")) {
					printerr();
					return false;
				}
			}
			// persist_dao
			if (!arr_d[0].trim().equals("com") || !arr_d[1].trim().equals("easywork")
					|| !arr_d[arr_d.length - 1].trim().equals("dao")) {
				printerr();
				return false;
			}
			for (String str : arr_d) {
				if (!VerifyUtils.isLowerStr(str) || str.contains(" ")) {
					printerr();
					return false;
				}
			}
		} catch (Exception e) {
			printerr();
			return false;
		}
		return true;
	}

	// 输出错误说明
	private static void printerr() {
		System.out.println("-config/core.properties文件配置不正确 示例如下-");
		System.out.println("persist.entity=com.easywork.自定义小写命名.entity");
		System.out.println("persist.dao=com.easywork.自定义小写命名.dao");
	}

	// 生成各层目录
	private static void makedirs() {
		// 获取持久层路径
		String persist_entity = ConfigUtils.getVal("config/core.properties", "persist.entity");
		String entity_path = MyFileUtils.getCurrentSrcPath() + persist_entity.replace(".", "\\") + "\\";
		String persist_dao = ConfigUtils.getVal("config/core.properties", "persist.dao");
		String dao_path = MyFileUtils.getCurrentSrcPath() + persist_dao.replace(".", "\\") + "\\";
		// 生成持久层目录
		File entity_dir = new File(entity_path);
		entity_dir.mkdirs();
		File dao_dir = new File(dao_path);
		dao_dir.mkdirs();

		// 获取controller层路径
		String ctrl_pack = persist_entity.replace("entity", "controller");
		String ctrl_path = MyFileUtils.getCurrentSrcPath() + ctrl_pack.replace(".", "\\") + "\\";
		// 生成controller层目录
		File ctrl_dir = new File(ctrl_path);
		ctrl_dir.mkdirs();

		// 获取service层路径
		String service_pack = persist_entity.replace("entity", "service");
		String service_path = MyFileUtils.getCurrentSrcPath() + service_pack.replace(".", "\\") + "\\";
		// 生成service层目录
		File service_dir = new File(service_path);
		service_dir.mkdirs();

		// 获取model层路径
		String model_pack = persist_entity.replace("entity", "model");
		String model_path = MyFileUtils.getCurrentSrcPath() + model_pack.replace(".", "\\") + "\\";
		// 生成model层目录
		File model_dir = new File(model_path);
		model_dir.mkdirs();
	}

	// 扫描entity生成controller/service两层代码
	private static void genCtrlAndSrvc() {
		// 获取持久层路径
		String persist_entity = ConfigUtils.getVal("config/core.properties", "persist.entity");
		String entity_path = MyFileUtils.getCurrentSrcPath() + persist_entity.replace(".", "\\") + "\\";
		File entity_dir = new File(entity_path);
		File[] files = entity_dir.listFiles();
		// 生成HomeController
		genDemoCtrl();
		// 遍历生成controller/service
		for (File file : files) {
			String name = file.getName().trim();
			int lastdot = name.lastIndexOf(".");
			String entity = name.substring(0, lastdot);
			// 生成代码
			genCtrl(entity);
			genService(entity);
		}
	}

	// 生成HomeController代码
	private static void genDemoCtrl() {
		String persist_entity = ConfigUtils.getVal("config/core.properties", "persist.entity");
		String ctrl_pack = persist_entity.replace("entity", "controller");
		String ctrl_path = MyFileUtils.getCurrentSrcPath() + ctrl_pack.replace(".", "\\") + "\\";

		StringBuilder sb = new StringBuilder("package " + ctrl_pack + ";\r\n\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import org.springframework.stereotype.Controller;\r\n");
		sb.append("import org.springframework.web.bind.annotation.PathVariable;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
		sb.append("import com.easycore.utils.BaseController;\r\n\r\n");
		sb.append("@Controller\r\n");
		sb.append("@RequestMapping(\"/home\")\r\n");
		sb.append("public class HomeController extends BaseController {\r\n\r\n");
		sb.append("\t@RequestMapping(\"/view\")\r\n");
		sb.append("\tpublic String " + "view() {\r\n");
		sb.append("\t\treturn \"/demo/view\";\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("\t@RequestMapping(\"/openWork/{view}\")\r\n");
		sb.append("\tpublic String " + "openWork(@PathVariable(\"view\") String view) {\r\n");
		sb.append("\t\treturn \"/demo/\" + view;\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("}");

		MyFileUtils.writeFile(ctrl_path, "HomeController.java", sb.toString(), 0);
	}

	// 根据entity生成controller代码
	private static void genCtrl(String entity) {
		String persist_entity = ConfigUtils.getVal("config/core.properties", "persist.entity");
		String ctrl_pack = persist_entity.replace("entity", "controller");
		String service_pack = persist_entity.replace("entity", "service");
		String ctrl_path = MyFileUtils.getCurrentSrcPath() + ctrl_pack.replace(".", "\\") + "\\";
		String custName = persist_entity.replace(".entity", "").replace("com.", "").replace("easywork.", "")
				.replace(".", "/");

		StringBuilder sb = new StringBuilder("package " + ctrl_pack + ";\r\n\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import org.springframework.stereotype.Controller;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
		sb.append("import com.easycore.utils.BaseController;\r\n");
		sb.append("import " + persist_entity + "." + entity + ";\r\n");
		sb.append("import " + service_pack + "." + entity + "Service" + ";\r\n\r\n");
		sb.append("@Controller\r\n");
		sb.append("@RequestMapping(\"/" + custName + "/" + entity + "\")\r\n");
		sb.append("public class " + entity + "Controller extends BaseController {\r\n");
		sb.append("\t@Autowired\r\n");
		sb.append("\tprivate " + entity + "Service " + entity.toLowerCase() + "Service;\r\n\r\n");
		sb.append("}");
		MyFileUtils.writeFile(ctrl_path, entity + "Controller.java", sb.toString(), 0);
	}

	// 根据entity生成service代码
	private static void genService(String entity) {
		String persist_entity = ConfigUtils.getVal("config/core.properties", "persist.entity");
		String persist_dao = ConfigUtils.getVal("config/core.properties", "persist.dao");
		String service_pack = persist_entity.replace("entity", "service");
		String service_path = MyFileUtils.getCurrentSrcPath() + service_pack.replace(".", "\\") + "\\";

		StringBuilder sb = new StringBuilder("package " + service_pack + ";\r\n\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import org.springframework.stereotype.Service;\r\n");
		sb.append("import org.springframework.transaction.annotation.Transactional;\r\n");
		sb.append("import " + persist_entity + "." + entity + ";\r\n");
		sb.append("import " + persist_dao + "." + entity + "Mapper" + ";\r\n\r\n");
		sb.append("@Transactional\r\n");
		sb.append("@Service\r\n");
		sb.append("public class " + entity + "Service {\r\n");
		sb.append("\t@Autowired\r\n");
		sb.append("\tprivate " + entity + "Mapper " + entity.toLowerCase() + "Mapper;\r\n\r\n");
		sb.append("\tpublic " + entity + "Mapper " + "get" + entity + "Mapper() {\r\n");
		sb.append("\t\treturn " + entity.toLowerCase() + "Mapper;\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("}");
		MyFileUtils.writeFile(service_path, entity + "Service.java", sb.toString(), 0);
	}

	// 备份mapper接口文件
	private static void backupMapper() {
		String persist_dao = ConfigUtils.getVal("config/core.properties", "persist.dao");
		String dao_path = persist_dao.replace(".", "\\") + "\\";
		File persist_dir = new File(MyFileUtils.getCurrentSrcPath() + dao_path);

		File[] files = persist_dir.listFiles();
		for (File file : files) {
			if (file.getName().contains("Mapper.java")) {
				MyFileUtils.copyFileKeepName(file.getAbsolutePath(), MyFileUtils.getCurrentSrcPath() + "tmpcode\\",
						MyFileUtils.FILE_OVERWRITE);
			}
		}
	}

	// 还原mapper接口文件
	private static void recoverMapper() {
		String persist_dao = ConfigUtils.getVal("config/core.properties", "persist.dao");
		String dao_path = persist_dao.replace(".", "\\") + "\\";
		File tmpcode_dir = new File(MyFileUtils.getCurrentSrcPath() + "tmpcode\\");
		File[] files = tmpcode_dir.listFiles();
		for (File file : files) {
			MyFileUtils.copyFileKeepName(file.getAbsolutePath(), MyFileUtils.getCurrentSrcPath() + dao_path,
					MyFileUtils.FILE_OVERWRITE);
			file.delete();
		}
	}

	// 设置工程src路径
	private static void setSrcPath() {
		String path = MyFileUtils.getCurrentSrcPath();
		ConfigUtils.setVal("config/core.properties", "project.src", path);
	}

	// 生成持久层代码
	private static void generate()
			throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<String>();
		Configuration config = new ConfigurationParser(warnings)
				.parseConfiguration(AutoCoder.class.getResourceAsStream("AutoCoder.xml"));
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, new DefaultShellCallback(true), warnings);
		myBatisGenerator.generate(null);
	}
}
