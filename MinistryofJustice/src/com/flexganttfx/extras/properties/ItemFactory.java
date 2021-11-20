// =============================================== //
// Recompile disabled. Please run Recaf with a JDK //
// =============================================== //

// Decompiled with: CFR 0.151
// Class Version: 8
package com.flexganttfx.extras.properties;

import com.flexganttfx.extras.properties.GanttChartBaseItemProvider;
import com.flexganttfx.extras.properties.GanttChartItemProvider;
import com.flexganttfx.extras.properties.GraphicsBaseItemProvider;
import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.extras.properties.layer.AgendaLinesLayerItemProvider;
import com.flexganttfx.extras.properties.layer.ChartLinesLayerItemProvider;
import com.flexganttfx.extras.properties.layer.DSTLineLayerItemProvider;
import com.flexganttfx.extras.properties.layer.GridLinesLayerItemProvider;
import com.flexganttfx.extras.properties.layer.HoverTimeIntervalLayerItemProvider;
import com.flexganttfx.extras.properties.layer.InnerLinesLayerItemProvider;
import com.flexganttfx.extras.properties.layer.LayoutLayerItemProvider;
import com.flexganttfx.extras.properties.layer.NowLineLayerItemProvider;
import com.flexganttfx.extras.properties.layer.SelectedTimeIntervalsLayerItemProvider;
import com.flexganttfx.extras.properties.layer.SystemLayerItemProvider;
import com.flexganttfx.extras.properties.layer.ZoomIntervalLayerItemProvider;
import com.flexganttfx.extras.properties.renderer.ActivityBarRendererItemProvider;
import com.flexganttfx.extras.properties.renderer.ActivityRendererItemProvider;
import com.flexganttfx.extras.properties.renderer.CompletableActivityRendererItemProvider;
import com.flexganttfx.extras.properties.renderer.RendererItemProvider;
import com.flexganttfx.extras.properties.timeline.DatelineItemProvider;
import com.flexganttfx.extras.properties.timeline.EventlineItemProvider;
import com.flexganttfx.extras.properties.timeline.TimelineItemProvider;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.layer.AgendaLinesLayer;
import com.flexganttfx.view.graphics.layer.ChartLinesLayer;
import com.flexganttfx.view.graphics.layer.DSTLineLayer;
import com.flexganttfx.view.graphics.layer.GridLinesLayer;
import com.flexganttfx.view.graphics.layer.HoverTimeIntervalLayer;
import com.flexganttfx.view.graphics.layer.InnerLinesLayer;
import com.flexganttfx.view.graphics.layer.LayoutLayer;
import com.flexganttfx.view.graphics.layer.NowLineLayer;
import com.flexganttfx.view.graphics.layer.SelectedTimeIntervalsLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import com.flexganttfx.view.graphics.layer.ZoomTimeIntervalLayer;
import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
import com.flexganttfx.view.graphics.renderer.CompletableActivityRenderer;
import com.flexganttfx.view.graphics.renderer.Renderer;
import com.flexganttfx.view.timeline.Dateline;
import com.flexganttfx.view.timeline.Eventline;
import com.flexganttfx.view.timeline.Timeline;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;

@SuppressWarnings({ "unused", "rawtypes" })
public class ItemFactory {
	@SuppressWarnings({ "unchecked" })
	private static final Map<Class, ItemProvider<?>> PROVIDER_MAP = new HashMap();

	public List<PropertySheet.Item> getItems(Object object) {
		ItemProvider<Object> itemProvider = this.findItemProvider(object);
		if (itemProvider == null) {
			return BeanPropertyUtils.getProperties(object);
		}
		return itemProvider.getPropertySheetItems(object);
	}

	public static <T> void registerItemProvider(Class<T> clazz, ItemProvider<T> itemProvider) {
		PROVIDER_MAP.put(clazz, itemProvider);
	}

	private ItemProvider<Object> findItemProvider(Object object) {
		return this.doFindItemProvider(object.getClass());
	}

	@SuppressWarnings({ "unchecked" })
	private ItemProvider<Object> doFindItemProvider(Class clazz) {
		do {
			ItemProvider<Object> itemProvider;
			if ((itemProvider = (ItemProvider<Object>) PROVIDER_MAP.get(clazz)) == null)
				continue;
			return itemProvider;
		} while ((clazz = clazz.getSuperclass()) != null && clazz != Object.class);
		return null;
	}

	static {
		PROVIDER_MAP.put(GanttChartBase.class, new GanttChartBaseItemProvider());
		PROVIDER_MAP.put(GanttChart.class, new GanttChartItemProvider());
		PROVIDER_MAP.put(GraphicsBase.class, new GraphicsBaseItemProvider());
		PROVIDER_MAP.put(Dateline.class, new DatelineItemProvider());
		PROVIDER_MAP.put(Timeline.class, new TimelineItemProvider());
		PROVIDER_MAP.put(Eventline.class, new EventlineItemProvider());
		PROVIDER_MAP.put(AgendaLinesLayer.class, new AgendaLinesLayerItemProvider());
		PROVIDER_MAP.put(ChartLinesLayer.class, new ChartLinesLayerItemProvider());
		PROVIDER_MAP.put(DSTLineLayer.class, new DSTLineLayerItemProvider());
		PROVIDER_MAP.put(GridLinesLayer.class, new GridLinesLayerItemProvider());
		PROVIDER_MAP.put(HoverTimeIntervalLayer.class, new HoverTimeIntervalLayerItemProvider());
		PROVIDER_MAP.put(InnerLinesLayer.class, new InnerLinesLayerItemProvider());
		PROVIDER_MAP.put(LayoutLayer.class, new LayoutLayerItemProvider());
		PROVIDER_MAP.put(NowLineLayer.class, new NowLineLayerItemProvider());
		PROVIDER_MAP.put(SelectedTimeIntervalsLayer.class, new SelectedTimeIntervalsLayerItemProvider());
		PROVIDER_MAP.put(SystemLayer.class, new SystemLayerItemProvider());
		PROVIDER_MAP.put(ZoomTimeIntervalLayer.class, new ZoomIntervalLayerItemProvider());
		PROVIDER_MAP.put(ActivityBarRenderer.class, new ActivityBarRendererItemProvider());
		PROVIDER_MAP.put(ActivityRendererItemProvider.class, new ActivityRendererItemProvider());
		PROVIDER_MAP.put(CompletableActivityRenderer.class, new CompletableActivityRendererItemProvider());
		PROVIDER_MAP.put(Renderer.class, new RendererItemProvider());
	}
}
