---
layout: post
title:  springMvc使用
date:   2018-07-19 08:00:00 +0800
categories: spring
tag: springMvc
---

* content
{:toc}


## 重定向和转发
1. 重定向   
浏览器->服务器servlet1->浏览器->服务器servlet2->浏览器   

```java

private void response401(ServletRequest req, ServletResponse resp, String msg) {
    try {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        //通过url传参
        httpServletResponse.sendRedirect("/selfmes/401?msg="+msg);
    } catch (IOException e) {
        LOGGER.error(e.getMessage());
    }

}

```

1. 转发
浏览器->服务器servlet1->服务器servlet2->浏览器   

```java
private void response401(ServletRequest req, ServletResponse resp, String msg) {
    try {
        //通过request传参
        req.setAttribute("msg",msg);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/401");
        requestDispatcher.forward(req, resp);

    } catch (IOException e) {
        LOGGER.error(e.getMessage());
    }catch (ServletException e) {
        LOGGER.error(e.getMessage());
    }
}
```

[JAVA 的服务器重定向：使用forward()方法转发请求和使用 sendRedirect()方法重定向的区别](https://www.cnblogs.com/flyingeagle/articles/6681270.html)


## 多文件上传
```
<form id="myform">
<table>
    <tr>
	<td>
	    <input type="file" id="file" name="file" />
	</td>
	<td>
	    <input type="text" id="sampleid" name="sampleid" />
	</td>
    </tr>
    <tr>
	<td>
	    <input type="file" id="file" name="file" />
	</td>
	<td>
	    <input type="text" id="sampleid" name="sampleid" />
	</td>
    </tr>
</table>
</form>
<input type="button" onclick="SubmitForm()" value="提交" />
    
<script type="text/javascript">
	function SubmitForm() {
		//获取表单中的数据
		var file = document.getElementById('myform');
		//FormDat对象
		var formobj = new FormData(file);
		//XMLHttpRequest对象
		var xmlobj = new XMLHttpRequest();
		//指定提交类型和选择要发送的地址
		xmlobj.open('post', 'http://localhost:8080/selfProduct/file/upload');
		// xmlobj.withCredentials = true; //设置传递cookie，如果不需要直接注释就好
		// xmlobj.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		// xmlobj.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		//发送数据
		xmlobj.send(formobj);
		xmlobj.onload = function () {
		    alert(xmlobj.responseText);//获取后台返回的数据
		}
	}

//参考：
//      var xmlHttp;  

//   function AjaxFunction(){  
//           createXMLHttpRequest();  
//           if(xmlHttp!=null){  
//       xmlHttp.onreadystatechange = callBack;  
//       xmlHttp.open("get/Post","URL",true/false);  
//       xmlHttp.send(null);  
//           }     
//   }     

//   //实例化XMLHttpRequest对象  
//   function createXMLHttpRequest(){  
//           if(window.XMLHttpRequest){  
//       xmlHttp = new XMLHttpRequest();   
//           }else if(window.ActiveXObject){  
//       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
//           }  
//   }  

//   //指定响应函数  
//   function callBack(){  
//           if(xmlHttp.readyState==4){  
//                   if(xmlHttp.status==200){  
//               //do something with xmlHttp.responseText;  
//               xmlHttp.responseText;  
//                   }     
//           }  
//   } 
</script>

//后端接收参数  与前端form name标签相同，数组之间一一对应，未多次验证是否会出错
public Map<String, String> upload(@RequestParam("file") CommonsMultipartFile[] upfiles,
		@RequestParam("sampleid") String[] sampleids) throws IOException {
	...
}

//封装成对象传入：
@RequestMapping("upload1")
@ResponseBody
public void upload1(Files files) throws IOException {
	...
}
public class Files {
	private List<ModelFile> files;
	//geter...seter...
}
public class ModelFile {
	private CommonsMultipartFile file;
	private String sampleid;
	//geter...seter...
}

//前端
<form id="myform">
<table>
    <tr>
	<td>
	    <input type="file" id="file" name="files[0].file" />
	</td>
	<td>
	    <input type="text" id="sampleid" name="files[0].sampleid" />
	</td>
    </tr>
    <tr>
	<td>
	    <input type="file" id="file" name="files[1].file" />
	</td>
	<td>
	    <input type="text" id="sampleid" name="files[1].sampleid" />
	</td>
    </tr>
</table>
</form>
<input type="button" onclick="SubmitForm()" value="提交" />

```

## 自定义消息装换器
* 自定义注解

```java

package com.self.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UserNoToName {

}


```
* 继承至AbstractHttpMessageConverter类 实现对应方法

```java
package com.self.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class MyMessageConverter extends AbstractHttpMessageConverter<Object> {


	public final static Charset UTF8 = Charset.forName("UTF-8");

	public MyMessageConverter() {
		// 设置我们媒体类型
		super(new MediaType("application", "json", UTF8));
	}

	// 标明本HttpMessageConverter处理所有类，过滤在这些
	@Override
	protected boolean supports(Class<?> aClass) {
		return true;
	}

	//发起请求，反序列化时调用
	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		System.out.println("readInternal");

		return null;
	}

	//放回数据，序列化时调用
	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		System.out.println("writeInternal");
		Class<? extends Object> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//自定义注解UserNoToName
			if (field.isAnnotationPresent(UserNoToName.class)) {
				try {
					field.setAccessible(true);
					String val = field.get(obj)==null?"":(String)field.get(obj);
//					field.set(obj, "df1");
//					field.set(obj, userMapper.findByLoginName(val).getDisplayName());
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		outputMessage.getBody().write(JSON.toJSONBytes(obj, 
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteMapNullValue, 
				SerializerFeature.WriteNullStringAsEmpty));
	}

}


```

* 安监项目使用例子

```java
package com.hlkj.basis.dao.repository.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 目前只支持基本类型和Collection的实现类
 * 一般使用BaseEntityRepository 的 findByIdIncludeFields方法
 */
@Target(value = { FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdConvertOther {
    /** jpa查询类
     * @return
     */
    Class<?> springClass();

    /** 返回类转换的字段
     * 若为空""则为方法返回的obj
     * @return
     */
    String returnField();

    /** 当前类查询的id字段
     * 默认空则为当前字段，不为空的话只有当前字段为null才会生效
     * @return
     */
    String sourceField() default "";

    /** 指定查询方法
     * 默认使用BaseEntityRepository 的 findByIdIncludeFields查询
     * 其余方法参数只支持id查询,一个参数
     * @return
     */
    String methodName() default "findByIdIncludeFields";
}

```

```java
package com.hlkj.basis.dao.repository.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlkj.commons.utils.ErrororUtil;
import com.hlkj.commons.utils.spring.SpringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author 徐其伟
 * @Description: 继承至jackson的消息装换器
 * springboot2默认使用MappingJackson2HttpMessageConverter做json序列化转换器
 * 使用继承对序列化之前进行增强，进行字段装换
 * @date 19-4-12 下午3:06
 */
public class IdConvertMessageConverter extends MappingJackson2HttpMessageConverter {


    private static Logger logger = LogManager.getLogger(IdConvertMessageConverter.class.getName());

    public IdConvertMessageConverter() {
    }

    public IdConvertMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    //放回数据，序列化时调用
    private void convertOther(Object res) throws Exception {
        if (res == null) {
            return;
        }
        Class<?> clazz = res.getClass();
        if (isBasisType(clazz)) {
            return;
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            for (Object o : (Collection) res) {
                convertOther(o);
            }
            return;
        }
        if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) res;
            for (Object o : map.keySet()) {
                convertOther( map.get(o));
            }
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = field.get(res);

            if (field.isAnnotationPresent(IdConvertOther.class)) { //判断自定义注解
                IdConvertOther convertOther = field.getAnnotation(IdConvertOther.class);
                //获取装换 原先的id
                Object val;
                //sourceField为空则为当前字段
                if (!"".equals(convertOther.sourceField())) {
                    //如果当前字段已经有值了就不再赋值
                    if (fieldValue != null) {
                        continue;
                    }
                    Field f = clazz.getDeclaredField(convertOther.sourceField());
                    f.setAccessible(true);
                    val = f.get(res);
                } else {
                    val = fieldValue;
                }
                if (val == null) {
                    continue;
                }
                if (val instanceof Collection) {
                    Collection collection = (Collection) val.getClass().newInstance();
                    for (Object o : (Collection) val) {
                        collection.add(getTargetValue(convertOther, o));
                    }
                    field.set(res, collection);
                } else {
                    field.set(res, getTargetValue(convertOther, val));
                }

            } else if (!isBasisType(field.getType())) { //不是八大基本类型
                convertOther(fieldValue);
            }

        }
    }

    private Object getTargetValue(IdConvertOther convertOther, Object val) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        if(!(val instanceof String)) {
            return val;
        }
        Object springBean = SpringUtils.getBean(convertOther.springClass());
        Object result;
        if(springBean instanceof BaseEntityRepository) {
            result = ((BaseEntityRepository) springBean).findByIdIncludeFields((String)val, convertOther.returnField());
        } else {
            Method method = getMethodByName(convertOther.methodName(), springBean.getClass());
            result = method.invoke(springBean, val);
        }
        if (result != null) {
            Object entity;
            if (result instanceof Optional) {
                Optional optional = (Optional) result;
                entity = optional.orElse(null);
            } else {
                entity = result;
            }
            if (entity != null) {
                if ("".equals(convertOther.returnField())){
                    val = entity;
                } else {
                    Class<?> entityClass = entity.getClass();
                    Field targetField = entityClass.getDeclaredField(convertOther.returnField());
                    targetField.setAccessible(true);
                    val = targetField.get(entity);
                }
            }
        }
        return val;
    }

    /**
     * 根据名称查找方法
     * 参数为String 或者 Object的
     * @param methodName
     * @param cls
     * @return
     */
    private Method getMethodByName(String methodName, Class cls) {
        for (Method m : cls.getMethods()) {
            if (m.getName().equals(methodName) && m.getParameterTypes().length == 1) {
                Class<?> type = m.getParameterTypes()[0];
                if(type.equals(Object.class) || type.equals(String.class)) {
                    return m;
                }
            }
        }
        ErrororUtil.runThrow(this, "反射获取方法", "找不到方法");
        return null;
    }

    /** 是否为八大基础类型及其扩展类
     * @param clazz
     * @return
     */
    private boolean isBasisType(Class clazz) {
        boolean b = true;
        boolean basic = clazz.isPrimitive();
        if(basic) {
            return b;
        }
        switch (clazz.getTypeName()) {
            case "java.lang.String":
                break;
            case "java.lang.Boolean":
                break;
            case "java.lang.Integer":
                break;
            case "java.lang.Double":
                break;
            case "java.lang.Long":
                break;
            case "java.lang.Float":
                break;
            case "java.lang.Short":
                break;
            case "java.lang.Byte":
                break;
            case "java.lang.Character":
                break;
            default:
                b = false;
                break;
        }
        return b;
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        long l1 = System.currentTimeMillis();
        try {
            convertOther(object);
        } catch (Exception e) {
            logger.error("自定义转换字段发生错误");
            logger.error(e);
        }
        System.out.println("writeInternal:" + (System.currentTimeMillis() - l1));

        super.writeInternal(object, type, outputMessage);
    }
}

```