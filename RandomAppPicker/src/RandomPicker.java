public class RandomPicker {
    public static void randomSingleDisplay(AppDisplayList apl){
        int rval = ((int)(Math.random()* apl.getListSize()));
        AppItem pick = apl.getList().get(rval);
        System.out.println(pick.completeName());
        TextWindow tw = new TextWindow(pick.getName(), pick.getPath());
        tw.dispose();
    }
}
