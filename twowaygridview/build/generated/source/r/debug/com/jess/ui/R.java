/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package com.jess.ui;

public final class R {
    public static final class attr {
        /** 
             Indicates that this list will always be drawn on top of solid, single-color
             opaque background. This allows the list to optimize drawing.
        
         <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int cacheColorHint=0x7f010007;
        /** <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int columnWidth=0x7f01000e;
        /** 
             When set to true, the selector will be drawn over the selected item.
             Otherwise the selector is drawn behind the selected item. The default
             value is false.
        
         <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int drawSelectorOnTop=0x7f010003;
        /** <p>Must be one or more (separated by '|') of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>top</code></td><td>0x30</td><td> Push object to the top of its container, not changing its size. </td></tr>
<tr><td><code>bottom</code></td><td>0x50</td><td> Push object to the bottom of its container, not changing its size. </td></tr>
<tr><td><code>left</code></td><td>0x03</td><td> Push object to the left of its container, not changing its size. </td></tr>
<tr><td><code>right</code></td><td>0x05</td><td> Push object to the right of its container, not changing its size. </td></tr>
<tr><td><code>center_vertical</code></td><td>0x10</td><td> Place object in the vertical center of its container, not changing its size. </td></tr>
<tr><td><code>fill_vertical</code></td><td>0x70</td><td> Grow the vertical size of the object if needed so it completely fills its container. </td></tr>
<tr><td><code>center_horizontal</code></td><td>0x01</td><td> Place object in the horizontal center of its container, not changing its size. </td></tr>
<tr><td><code>fill_horizontal</code></td><td>0x07</td><td> Grow the horizontal size of the object if needed so it completely fills its container. </td></tr>
<tr><td><code>center</code></td><td>0x11</td><td> Place the object in the center of its container in both the vertical and horizontal axis, not changing its size. </td></tr>
<tr><td><code>fill</code></td><td>0x77</td><td> Grow the horizontal and vertical size of the object if needed so it completely fills its container. </td></tr>
<tr><td><code>clip_vertical</code></td><td>0x80</td><td>
             Additional option that can be set to have the top and/or bottom edges of
             the child clipped to its container's bounds.
             The clip will be based on the vertical gravity: a top gravity will clip the bottom
             edge, a bottom gravity will clip the top edge, and neither will clip both edges.
        </td></tr>
<tr><td><code>clip_horizontal</code></td><td>0x08</td><td>
             Additional option that can be set to have the left and/or right edges of
             the child clipped to its container's bounds.
             The clip will be based on the horizontal gravity: a left gravity will clip the right
             edge, a right gravity will clip the left edge, and neither will clip both edges.
        </td></tr>
</table>
         */
        public static int gravity=0x7f010000;
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static int gridViewStyle=0x7f010001;
        /** <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int horizontalSpacing=0x7f01000b;
        /**  Drawable used to indicate the currently selected item in the list. 
         <p>May be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
<p>May be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
         */
        public static int listSelector=0x7f010002;
        /** <p>May be an integer value, such as "<code>100</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
<p>May be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>auto_fit</code></td><td>-1</td><td></td></tr>
</table>
         */
        public static int numColumns=0x7f010010;
        /** <p>May be an integer value, such as "<code>100</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
<p>May be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>auto_fit</code></td><td>-1</td><td></td></tr>
</table>
         */
        public static int numRows=0x7f010011;
        /** <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int rowHeight=0x7f01000f;
        /** <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>vertical</code></td><td>0</td><td> Scroll up vertically. This is the default value. </td></tr>
<tr><td><code>horizontal</code></td><td>1</td><td> Scroll horizontally. </td></tr>
</table>
         */
        public static int scrollDirectionLandscape=0x7f01000a;
        /** 
             Use this attribute to control which direction the GridView scrolls when in
             portrait orientation
        
         <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>vertical</code></td><td>0</td><td> Scroll up vertically. This is the default value. </td></tr>
<tr><td><code>horizontal</code></td><td>1</td><td> Scroll horizontally. </td></tr>
</table>
         */
        public static int scrollDirectionPortrait=0x7f010009;
        /** 
             When set to true, the list uses a drawing cache during scrolling.
             This makes the rendering faster but uses more memory. The default
             value is true.
        
         <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int scrollingCache=0x7f010005;
        /** 
             Enables the fast scroll thumb that can be dragged to quickly scroll through
             the list.
        
 <attr name="fastScrollEnabled" format="boolean" /> 

             When set to true, the list will use a more refined calculation
             method based on the pixels height of the items visible on screen. This
             property is set to true by default but should be set to false if your adapter
             will display items of varying heights. When this property is set to true and
             your adapter displays items of varying heights, the scrollbar thumb will
             change size as the user scrolls through the list. When set to fale, the list
             will use only the number of items in the adapter and the number of items visible
             on screen to determine the scrollbar's properties.
        
         <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int smoothScrollbar=0x7f010008;
        /**  Used by ListView and GridView to stack their content from the bottom. 
         <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int stackFromBottom=0x7f010004;
        /** <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>none</code></td><td>0</td><td></td></tr>
<tr><td><code>spacingWidth</code></td><td>1</td><td></td></tr>
<tr><td><code>columnWidth</code></td><td>2</td><td></td></tr>
<tr><td><code>spacingWidthUniform</code></td><td>3</td><td></td></tr>
</table>
         */
        public static int stretchMode=0x7f01000d;
        /** 
             When set to true, the list will filter results as the user types. The
             List's adapter must support the Filterable interface for this to work.
        
 <attr name="textFilterEnabled" format="boolean" /> 

             Sets the transcript mode for the list. In transcript mode, the list
             scrolls to the bottom to make new items visible when they are added.
        
         <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>disabled</code></td><td>0</td><td> Disables transcript mode. This is the default value. </td></tr>
<tr><td><code>normal</code></td><td>1</td><td>
                 The list will automatically scroll to the bottom when
                 a data set change notification is received and only if the last item is
                 already visible on screen.
            </td></tr>
<tr><td><code>alwaysScroll</code></td><td>2</td><td>
                 The list will automatically scroll to the bottom, no matter what items
                 are currently visible.
            </td></tr>
</table>
         */
        public static int transcriptMode=0x7f010006;
        /** <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static int verticalSpacing=0x7f01000c;
    }
    public static final class drawable {
        public static int ic_launcher=0x7f020000;
        public static int spinner_black_76=0x7f020001;
    }
    public static final class id {
        public static int alwaysScroll=0x7f04000e;
        public static int auto_fit=0x7f040015;
        public static int bottom=0x7f040001;
        public static int center=0x7f040008;
        public static int center_horizontal=0x7f040006;
        public static int center_vertical=0x7f040004;
        public static int clip_horizontal=0x7f04000b;
        public static int clip_vertical=0x7f04000a;
        public static int columnWidth=0x7f040013;
        public static int disabled=0x7f04000c;
        public static int fill=0x7f040009;
        public static int fill_horizontal=0x7f040007;
        public static int fill_vertical=0x7f040005;
        public static int gridview=0x7f040016;
        public static int horizontal=0x7f040010;
        public static int left=0x7f040002;
        public static int none=0x7f040011;
        public static int normal=0x7f04000d;
        public static int right=0x7f040003;
        public static int spacingWidth=0x7f040012;
        public static int spacingWidthUniform=0x7f040014;
        public static int top=0x7f040000;
        public static int vertical=0x7f04000f;
    }
    public static final class layout {
        public static int main=0x7f030000;
    }
    public static final class string {
        public static int app_name=0x7f050000;
    }
    public static final class styleable {
        /** Attributes that can be used with a TwoWayAbsListView.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #TwoWayAbsListView_cacheColorHint com.jess.ui:cacheColorHint}</code></td><td>
             Indicates that this list will always be drawn on top of solid, single-color
             opaque background.</td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_drawSelectorOnTop com.jess.ui:drawSelectorOnTop}</code></td><td>
             When set to true, the selector will be drawn over the selected item.</td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_listSelector com.jess.ui:listSelector}</code></td><td> Drawable used to indicate the currently selected item in the list.</td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_scrollDirectionLandscape com.jess.ui:scrollDirectionLandscape}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_scrollDirectionPortrait com.jess.ui:scrollDirectionPortrait}</code></td><td>
             Use this attribute to control which direction the GridView scrolls when in
             portrait orientation
        </td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_scrollingCache com.jess.ui:scrollingCache}</code></td><td>
             When set to true, the list uses a drawing cache during scrolling.</td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_smoothScrollbar com.jess.ui:smoothScrollbar}</code></td><td>
             Enables the fast scroll thumb that can be dragged to quickly scroll through
             the list.</td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_stackFromBottom com.jess.ui:stackFromBottom}</code></td><td> Used by ListView and GridView to stack their content from the bottom.</td></tr>
           <tr><td><code>{@link #TwoWayAbsListView_transcriptMode com.jess.ui:transcriptMode}</code></td><td>
             When set to true, the list will filter results as the user types.</td></tr>
           </table>
           @see #TwoWayAbsListView_cacheColorHint
           @see #TwoWayAbsListView_drawSelectorOnTop
           @see #TwoWayAbsListView_listSelector
           @see #TwoWayAbsListView_scrollDirectionLandscape
           @see #TwoWayAbsListView_scrollDirectionPortrait
           @see #TwoWayAbsListView_scrollingCache
           @see #TwoWayAbsListView_smoothScrollbar
           @see #TwoWayAbsListView_stackFromBottom
           @see #TwoWayAbsListView_transcriptMode
         */
        public static final int[] TwoWayAbsListView = {
            0x7f010002, 0x7f010003, 0x7f010004, 0x7f010005,
            0x7f010006, 0x7f010007, 0x7f010008, 0x7f010009,
            0x7f01000a
        };
        /**
          <p>
          @attr description
          
             Indicates that this list will always be drawn on top of solid, single-color
             opaque background. This allows the list to optimize drawing.
        


          <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          <p>This is a private symbol.
          @attr name com.jess.ui:cacheColorHint
        */
        public static final int TwoWayAbsListView_cacheColorHint = 5;
        /**
          <p>
          @attr description
          
             When set to true, the selector will be drawn over the selected item.
             Otherwise the selector is drawn behind the selected item. The default
             value is false.
        


          <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          <p>This is a private symbol.
          @attr name com.jess.ui:drawSelectorOnTop
        */
        public static final int TwoWayAbsListView_drawSelectorOnTop = 1;
        /**
          <p>
          @attr description
           Drawable used to indicate the currently selected item in the list. 


          <p>May be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
<p>May be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
          <p>This is a private symbol.
          @attr name com.jess.ui:listSelector
        */
        public static final int TwoWayAbsListView_listSelector = 0;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#scrollDirectionLandscape}
          attribute's value can be found in the {@link #TwoWayAbsListView} array.


          <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>vertical</code></td><td>0</td><td> Scroll up vertically. This is the default value. </td></tr>
<tr><td><code>horizontal</code></td><td>1</td><td> Scroll horizontally. </td></tr>
</table>
          @attr name com.jess.ui:scrollDirectionLandscape
        */
        public static final int TwoWayAbsListView_scrollDirectionLandscape = 8;
        /**
          <p>
          @attr description
          
             Use this attribute to control which direction the GridView scrolls when in
             portrait orientation
        


          <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>vertical</code></td><td>0</td><td> Scroll up vertically. This is the default value. </td></tr>
<tr><td><code>horizontal</code></td><td>1</td><td> Scroll horizontally. </td></tr>
</table>
          <p>This is a private symbol.
          @attr name com.jess.ui:scrollDirectionPortrait
        */
        public static final int TwoWayAbsListView_scrollDirectionPortrait = 7;
        /**
          <p>
          @attr description
          
             When set to true, the list uses a drawing cache during scrolling.
             This makes the rendering faster but uses more memory. The default
             value is true.
        


          <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          <p>This is a private symbol.
          @attr name com.jess.ui:scrollingCache
        */
        public static final int TwoWayAbsListView_scrollingCache = 3;
        /**
          <p>
          @attr description
          
             Enables the fast scroll thumb that can be dragged to quickly scroll through
             the list.
        
 <attr name="fastScrollEnabled" format="boolean" /> 

             When set to true, the list will use a more refined calculation
             method based on the pixels height of the items visible on screen. This
             property is set to true by default but should be set to false if your adapter
             will display items of varying heights. When this property is set to true and
             your adapter displays items of varying heights, the scrollbar thumb will
             change size as the user scrolls through the list. When set to fale, the list
             will use only the number of items in the adapter and the number of items visible
             on screen to determine the scrollbar's properties.
        


          <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          <p>This is a private symbol.
          @attr name com.jess.ui:smoothScrollbar
        */
        public static final int TwoWayAbsListView_smoothScrollbar = 6;
        /**
          <p>
          @attr description
           Used by ListView and GridView to stack their content from the bottom. 


          <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          <p>This is a private symbol.
          @attr name com.jess.ui:stackFromBottom
        */
        public static final int TwoWayAbsListView_stackFromBottom = 2;
        /**
          <p>
          @attr description
          
             When set to true, the list will filter results as the user types. The
             List's adapter must support the Filterable interface for this to work.
        
 <attr name="textFilterEnabled" format="boolean" /> 

             Sets the transcript mode for the list. In transcript mode, the list
             scrolls to the bottom to make new items visible when they are added.
        


          <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>disabled</code></td><td>0</td><td> Disables transcript mode. This is the default value. </td></tr>
<tr><td><code>normal</code></td><td>1</td><td>
                 The list will automatically scroll to the bottom when
                 a data set change notification is received and only if the last item is
                 already visible on screen.
            </td></tr>
<tr><td><code>alwaysScroll</code></td><td>2</td><td>
                 The list will automatically scroll to the bottom, no matter what items
                 are currently visible.
            </td></tr>
</table>
          <p>This is a private symbol.
          @attr name com.jess.ui:transcriptMode
        */
        public static final int TwoWayAbsListView_transcriptMode = 4;
        /** Attributes that can be used with a TwoWayGridView.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #TwoWayGridView_columnWidth com.jess.ui:columnWidth}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayGridView_gravity com.jess.ui:gravity}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayGridView_horizontalSpacing com.jess.ui:horizontalSpacing}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayGridView_numColumns com.jess.ui:numColumns}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayGridView_numRows com.jess.ui:numRows}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayGridView_rowHeight com.jess.ui:rowHeight}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayGridView_stretchMode com.jess.ui:stretchMode}</code></td><td></td></tr>
           <tr><td><code>{@link #TwoWayGridView_verticalSpacing com.jess.ui:verticalSpacing}</code></td><td></td></tr>
           </table>
           @see #TwoWayGridView_columnWidth
           @see #TwoWayGridView_gravity
           @see #TwoWayGridView_horizontalSpacing
           @see #TwoWayGridView_numColumns
           @see #TwoWayGridView_numRows
           @see #TwoWayGridView_rowHeight
           @see #TwoWayGridView_stretchMode
           @see #TwoWayGridView_verticalSpacing
         */
        public static final int[] TwoWayGridView = {
            0x7f010000, 0x7f01000b, 0x7f01000c, 0x7f01000d,
            0x7f01000e, 0x7f01000f, 0x7f010010, 0x7f010011
        };
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#columnWidth}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.jess.ui:columnWidth
        */
        public static final int TwoWayGridView_columnWidth = 4;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#gravity}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>Must be one or more (separated by '|') of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>top</code></td><td>0x30</td><td> Push object to the top of its container, not changing its size. </td></tr>
<tr><td><code>bottom</code></td><td>0x50</td><td> Push object to the bottom of its container, not changing its size. </td></tr>
<tr><td><code>left</code></td><td>0x03</td><td> Push object to the left of its container, not changing its size. </td></tr>
<tr><td><code>right</code></td><td>0x05</td><td> Push object to the right of its container, not changing its size. </td></tr>
<tr><td><code>center_vertical</code></td><td>0x10</td><td> Place object in the vertical center of its container, not changing its size. </td></tr>
<tr><td><code>fill_vertical</code></td><td>0x70</td><td> Grow the vertical size of the object if needed so it completely fills its container. </td></tr>
<tr><td><code>center_horizontal</code></td><td>0x01</td><td> Place object in the horizontal center of its container, not changing its size. </td></tr>
<tr><td><code>fill_horizontal</code></td><td>0x07</td><td> Grow the horizontal size of the object if needed so it completely fills its container. </td></tr>
<tr><td><code>center</code></td><td>0x11</td><td> Place the object in the center of its container in both the vertical and horizontal axis, not changing its size. </td></tr>
<tr><td><code>fill</code></td><td>0x77</td><td> Grow the horizontal and vertical size of the object if needed so it completely fills its container. </td></tr>
<tr><td><code>clip_vertical</code></td><td>0x80</td><td>
             Additional option that can be set to have the top and/or bottom edges of
             the child clipped to its container's bounds.
             The clip will be based on the vertical gravity: a top gravity will clip the bottom
             edge, a bottom gravity will clip the top edge, and neither will clip both edges.
        </td></tr>
<tr><td><code>clip_horizontal</code></td><td>0x08</td><td>
             Additional option that can be set to have the left and/or right edges of
             the child clipped to its container's bounds.
             The clip will be based on the horizontal gravity: a left gravity will clip the right
             edge, a right gravity will clip the left edge, and neither will clip both edges.
        </td></tr>
</table>
          @attr name com.jess.ui:gravity
        */
        public static final int TwoWayGridView_gravity = 0;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#horizontalSpacing}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.jess.ui:horizontalSpacing
        */
        public static final int TwoWayGridView_horizontalSpacing = 1;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#numColumns}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>May be an integer value, such as "<code>100</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
<p>May be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>auto_fit</code></td><td>-1</td><td></td></tr>
</table>
          @attr name com.jess.ui:numColumns
        */
        public static final int TwoWayGridView_numColumns = 6;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#numRows}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>May be an integer value, such as "<code>100</code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
<p>May be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>auto_fit</code></td><td>-1</td><td></td></tr>
</table>
          @attr name com.jess.ui:numRows
        */
        public static final int TwoWayGridView_numRows = 7;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#rowHeight}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.jess.ui:rowHeight
        */
        public static final int TwoWayGridView_rowHeight = 5;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#stretchMode}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>none</code></td><td>0</td><td></td></tr>
<tr><td><code>spacingWidth</code></td><td>1</td><td></td></tr>
<tr><td><code>columnWidth</code></td><td>2</td><td></td></tr>
<tr><td><code>spacingWidthUniform</code></td><td>3</td><td></td></tr>
</table>
          @attr name com.jess.ui:stretchMode
        */
        public static final int TwoWayGridView_stretchMode = 3;
        /**
          <p>This symbol is the offset where the {@link com.jess.ui.R.attr#verticalSpacing}
          attribute's value can be found in the {@link #TwoWayGridView} array.


          <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.jess.ui:verticalSpacing
        */
        public static final int TwoWayGridView_verticalSpacing = 2;
    };
}