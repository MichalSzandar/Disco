package michal.projects;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public final class Utils 
{
    @SuppressWarnings("exports")
    public static Color generateRandomColor(Random random)
    {
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    @SuppressWarnings("exports")
    public static Color calculateAverageColor(ArrayList<Color> colors)
    {
        if(colors.size()==0)
            return Color.WHITE;
            
        double blue = 0;
        double green = 0;
        double red = 0;
        for(Color color : colors)
        {
            blue += color.getBlue();
            red += color.getRed();
            green += color.getGreen();
        }
        blue/=colors.size();
        green/=colors.size();
        red/=colors.size();
        return Color.color(blue, green, red);
    }
}
