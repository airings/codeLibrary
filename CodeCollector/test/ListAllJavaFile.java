import java.io.File;
import java.io.FilenameFilter;




public class ListAllJavaFile {
	public static void main(String[] args) {
		File f = new File("/Users/airings/Programming/传智播客_Java培训_毕向东_Java基础/传智播客_Java培训_毕向东_Java基础源代码Codes/day18");
		String[] namesStrings = f.list( new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}
		});
		for (String string : namesStrings) {
			System.out.println(string);
		}
	}

}
