package cn.clxy.studio.common.aop;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 注册组件使用带有package的类名。<br>
 * 仅仅用在Action的注册上@20131110，因为同名Action的存在。
 * @author clxy
 */
public class PackageNameGenerator extends AnnotationBeanNameGenerator {

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		return definition.getBeanClassName();
	}
}
