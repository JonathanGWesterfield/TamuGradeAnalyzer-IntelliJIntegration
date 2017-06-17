import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.scene.Node.*;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.logging.*;


class CustomStackedBarChart extends StackedBarChart<String, Number>
{

    public CustomStackedBarChart( CategoryAxis xAxis, Axis<Number> yAxis )
    {
        super( xAxis, yAxis );

        setAnimated( false );
        setLegendVisible( false );
        setTitleSide( Side.TOP );
        setTitle( "SSPP Diurnal Symbol Rate" );
        setMinHeight( 500 );
        setMinWidth( 1000 );
    }

    @Override
    protected void layoutPlotChildren()
    {
        super.layoutPlotChildren();

        // Each individual Data element has its own bar.
        // TODO add label to each bar.
        for ( Series<String, Number> series : getData() )
        {
            for ( Data<String, Number> data : series.getData() )
            {
                StackPane bar = (StackPane) data.getNode();

                Label label = null;

                for ( Node node : bar.getChildrenUnmodifiable() )
                {
                    //LOGGER.debug( "Bar has child {}, {}.", node,
                    //        node.getClass() );
                    if ( node instanceof Label )
                    {
                        label = (Label) node;
                        break;
                    }
                }

                if ( label == null )
                {
                    label = new Label( series.getName() );
                    label.setRotate( 90.0 );
                    bar.getChildren().add( label );
                }
                else
                {
                    label.setText( series.getName() );
                }
            }
        }
    }
}