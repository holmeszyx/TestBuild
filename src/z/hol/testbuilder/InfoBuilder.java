package z.hol.testbuilder;

/**
 * 本文生成器
 * Created by holmes on 9/6/14.
 */
public class InfoBuilder {

    private StringBuilder mSb;

    public InfoBuilder(){
        mSb = new StringBuilder(512);
    }

    /**
     * 添加一个头
     * @param title
     */
    public void addTitle(String title){
        if (title != null){
            final int len = title.length();
            final int flagLen = len + 1;
            StringBuilder flag = new StringBuilder(flagLen);
            for (int i = 0; i < flagLen; i ++){
                flag.append("=");
            }
            String flagStr = flag.toString();
            if (mSb.length() > 0){
                newLine();
            }
            mSb.append(flagStr);
            newLine();
            mSb.append(title);
            newLine();
            mSb.append(flagStr);
            newLine();
        }
    }

    /**
     * 添加子标题
     * @param subTitle
     */
    public void addSubTitle(String subTitle){
        if (subTitle != null){
            final int len = subTitle.length() + 2;
            final int flagLen = len + 1;
            StringBuilder flag = new StringBuilder(flagLen);
            for (int i = 0; i < flagLen; i ++){
                flag.append("--");
            }
            String flagStr = flag.toString();
            if (mSb.length() > 0){
                newLine();
            }
            mSb.append(flagStr);
            newLine();
            mSb.append("  ");
            mSb.append(subTitle);
            newLine();
            mSb.append(flagStr);
            newLine();
        }

    }

    /**
     * 添加信息
     * @param info
     */
    public void addInfo(String info){
        if (info != null){
            mSb.append(info);
            newLine();
        }
    }

    /**
     * 添加信息
     * @param key
     * @param value
     */
    public void addInfo(String key, Object value){
        addInfo("" + key + ": " + value);
    }

    /**
     * 换行
     */
    public void newLine(){
        mSb.append("\n");
    }

    public int length(){
        return mSb.length();
    }

    public String toString(){
        return mSb.toString();
    }
}
