package z.hol.testbuilder;


/**
 * 一些格式化的工具
 * 
 * @author holmes
 * 
 */
public class Formatter {
	
	/**
	 * 文件大小单位
	 * @author holmes
	 *
	 */
	public static enum SizeSuffix{
		B("B"),
		KB("KB"),
		MB("MB"),
		GB("GB"),
		TB("TB"),
		PB("PB");

		/** 单位的字符 */
		final private String mSuffix;
		private SizeSuffix(String suffix) {
			// This is Auto-generated constructor stub
			mSuffix = suffix;
		}
		
		@Override
		public String toString() {
			// This is Auto-generated method stub
			return mSuffix;
		}
	}
	
	/**
	 * 格式化后的大小
	 * @author holmes
	 *
	 */
	public static final class FormattedSize{
		/** 数值 */
		long number;
		
		/** 格式化后的字符数值 */
		String value;
		/** 格式化后的字符单位 */
		SizeSuffix suffix;
		
		
		public FormattedSize(long number, String value, SizeSuffix suffix) {
			super();
			this.number = number;
			this.value = value;
			this.suffix = suffix;
		}

		/** 数值 */
		public long getNumber() {
			return number;
		}

		/** 格式化后的字符数值 */
		public String getValue() {
			return value;
		}

		/** 格式化后的字符单位 */
		public SizeSuffix getSuffix() {
			return suffix;
		}
		
		
		
	}

	/**
	 * 格式化大小
	 * @param number 文件大小
	 * @param shorter 是否是短格式化, true 则只保留一位小数, false, 两位小数
	 * @param fs 格式化后的结果分解, 如果不会null, 则会在格式化后, 将填充结果分解项
	 * @return
	 */
	public static String formatFileSize(long number, boolean shorter, FormattedSize fs) {
		SizeSuffix suffix = SizeSuffix.B;

		String value;
		if (number != 0l){
			float result = number;
			if (result > 900) {
				suffix = SizeSuffix.KB;
				result = result / 1024;
			}
			if (result > 900) {
				suffix = SizeSuffix.MB;
				result = result / 1024;
			}
			if (result > 900) {
				suffix = SizeSuffix.GB;
				result = result / 1024;
			}
			if (result > 900) {
				suffix = SizeSuffix.TB;
				result = result / 1024;
			}
			if (result > 900) {
				suffix = SizeSuffix.PB;
				result = result / 1024;
			}

			if (result < 1) {
				value = String.format("%.2f", result);
			} else if (result < 10) {
				if (shorter) {
					value = String.format("%.1f", result);
				} else {
					value = String.format("%.2f", result);
				}
			} else if (result < 100) {
				if (shorter) {
					value = String.format("%.0f", result);
				} else {
					value = String.format("%.2f", result);
				}
			} else {
				value = String.format("%.0f", result);
			}
		}else{
			value = "0";
		}
		
		if (fs != null){
			fs.number = number;
			fs.value = value;
			fs.suffix = suffix;
		}
		
		return value + suffix.toString();
	}

}