### Public Function request no-public parameters ###

A public class has a function which accessed to a no public class, please have a look the following codes, this will stop create reflection information and all project will be failed to compile.

You will see a compile error in your console like this:
```
The type KnowIssues.ClassPackageVisable is not visible
```
```
public class KnowIssues {
	
	static class ClassPackageVisable{
		
	}
	
	@Reflect_Full
	public static class KnowIssueFunctionWithNoPublicParameters{
		
		private String name;
		
		public void setClass(ClassPackageVisable clazz){
			
		}
		
		public ClassPackageVisable getClassA(){
			return null;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	@Reflectable(methods=false)
	public static class KnowIssueFunctionWithNoPublicParameters_HowFix{
		
		private String name;
		
		public void setClass(ClassPackageVisable clazz){
			
		}
		
		public ClassPackageVisable getClassA(){
			return null;
		}

		@HasReflect
		public void setName(String name) {
			this.name = name;
		}

		@HasReflect
		public String getName() {
			return name;
		}
	}
}
```

But with class "KnowIssueFunctionWithNoPublicParameters\_HowFix", your compile will be all good, and following the assert list below
```
public void testNoPublicParameterIssue(){
  	ClassType type = TypeOracle.Instance.getClassType(KnowIssues.KnowIssueFunctionWithNoPublicParameters_HowFix.class);
  	assertTrue(type != null);
  	
  	assertTrue(type.findMethod("getClassA") == null);
  	assertTrue(type.findMethod("setClass") == null);
  	
  	assertTrue(type.findMethod("setName") != null);
  	assertTrue(type.findMethod("getName") != null);
  	assertTrue(type.findField("name") != null);
  }
```

### IllegalAccessException ###
You will see some thing like this:
[ERROR](ERROR.md) java.lang.IllegalAccessException: Class com.gwtent.gen.reflection.ReflectAllInOneCreator can not access a member of class abc.def.XXXX with modifiers "public abstract";

Please have a look this bug report, to avoid it you need change your class/interface/annotations to public.

http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071593