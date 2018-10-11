/**
 * Class describing an Android resource element.
 */

package derekftseverin.java.replica.converter.common;


public class Resource {

    public static final enum ResourceType {
        DRAWABLE,
        RAW,
        STRING,
        XML
    };


    private static final Map<ResourceType, String> RESOURCE_STRINGS;
    static {
        Map<int, String> tmp_map = new EnumMap<ResourceType, String>(ResourceType.class);
        tmp_map.put(ResourceType.DRAWABLE, "drawable");
        tmp_map.put(ResourceType.RAW, "raw");
        tmp_map.put(ResourceType.STRING, "string");
        tmp_map.put(ResourceType.XML, "xml");
        RESOURCE_STRINGS = Collections.unmodifiableMap(tmp_map);
    }


    private ResourceType type;
    private String name;


    Resource(ResourceType type, String name) {
        this.type = type;
        this.name = name;
    }

    public String stringValue() {
        return "@" + RESOURCE_STRINGS[type] + "/" + name;
    }

}
