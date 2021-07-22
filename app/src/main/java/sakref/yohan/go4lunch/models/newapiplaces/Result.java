package sakref.yohan.go4lunch.models.newapiplaces;

public class Result {
    private AddressComponent[] addressComponents;
    private String adrAddress;
    private String businessStatus;
    private String formattedAddress;
    private String formattedPhoneNumber;
    private String internationalPhoneNumber;
    private Geometry geometry;
    private String icon;
    private String name;
    private Photo[] photos;
    private String placeId;
    private PlusCode plusCode;
    private String[] types;
    private String url;
    private Long utcOffset;
    private String vicinity;

    public AddressComponent[] getAddressComponents() { return addressComponents; }
    public void setAddressComponents(AddressComponent[] value) { this.addressComponents = value; }

    public String getAdrAddress() { return adrAddress; }
    public void setAdrAddress(String value) { this.adrAddress = value; }

    public String getBusinessStatus() { return businessStatus; }
    public void setBusinessStatus(String value) { this.businessStatus = value; }

    public String getFormattedAddress() { return formattedAddress; }
    public void setFormattedAddress(String value) { this.formattedAddress = value; }

    public String getFormattedPhoneNumber() { return formattedPhoneNumber; }
    public void setFormattedPhoneNumber(String value) { this.formattedPhoneNumber = value; }

    public String getInternationalPhoneNumber() { return internationalPhoneNumber; }
    public void setInternationalPhoneNumber(String value) { this.internationalPhoneNumber = value; }

    public Geometry getGeometry() { return geometry; }
    public void setGeometry(Geometry value) { this.geometry = value; }

    public String getIcon() { return icon; }
    public void setIcon(String value) { this.icon = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public Photo[] getPhotos() { return photos; }
    public void setPhotos(Photo[] value) { this.photos = value; }

    public String getPlaceId() { return placeId; }
    public void setPlaceId(String value) { this.placeId = value; }

    public PlusCode getPlusCode() { return plusCode; }
    public void setPlusCode(PlusCode value) { this.plusCode = value; }

    public String[] getTypes() { return types; }
    public void setTypes(String[] value) { this.types = value; }

    public String getUrl() { return url; }
    public void setUrl(String value) { this.url = value; }

    public Long getUtcOffset() { return utcOffset; }
    public void setUtcOffset(Long value) { this.utcOffset = value; }

    public String getVicinity() { return vicinity; }
    public void setVicinity(String value) { this.vicinity = value; }
}
