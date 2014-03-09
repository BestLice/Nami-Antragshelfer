package nami.connector.namitypes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import nami.connector.NamiConnector;
import nami.connector.NamiResponse;
import nami.connector.NamiURIBuilder;
import nami.connector.exception.NamiApiException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.google.gson.reflect.TypeToken;

// TODO: Implementiere hascode (?)
public class NamiTaetigkeitAssignmentListElement implements Comparable<NamiTaetigkeitAssignmentListElement> {
    private static final int MAX_TAETIGKEITEN = 1000;
    
    public static class EntriesType {
        @SuppressWarnings("unused")
		private String gruppierung;
        @SuppressWarnings("unused")
		private String caeaGroup;
        @SuppressWarnings("unused")
		private String untergliederung;
        @SuppressWarnings("unused")
		private String anlagedatum;
        @SuppressWarnings("unused")
		private String aktivVon;
        @SuppressWarnings("unused")
		private String aktivBis;
        @SuppressWarnings("unused")
		private String caeaGroupForGf;
        @SuppressWarnings("unused")
		private String taetigkeit;
        @SuppressWarnings("unused")
		private String rowCssClass;
        @SuppressWarnings("unused")
		private String mitglied;
    }

    @SuppressWarnings("unused")
	private String descriptor;
    @SuppressWarnings("unused")
	private EntriesType entries;
    @SuppressWarnings("unused")
	private String name;
    @SuppressWarnings("unused")
	private String representedClass;
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NamiTaetigkeitAssignmentListElement)) {
            return false;
        }
        NamiTaetigkeitAssignmentListElement other = (NamiTaetigkeitAssignmentListElement) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(NamiTaetigkeitAssignmentListElement o) {
        return Integer.valueOf(this.id).compareTo(Integer.valueOf(o.id));
    }
    
    public static Collection<NamiTaetigkeitAssignmentListElement> getTaetigkeiten(NamiConnector con, int id) throws ClientProtocolException, IOException, NamiApiException {
        NamiURIBuilder builder = con.getURIBuilder(NamiURIBuilder.URL_NAMI_TAETIGKEIT);
        builder.appendPath(Integer.toString(id));
        builder.appendPath("flist");
        builder.setParameter("limit", Integer.toString(MAX_TAETIGKEITEN));
        builder.setParameter("page", Integer.toString(0));
        builder.setParameter("start", Integer.toString(0));
        HttpGet httpGet = new HttpGet(builder.build());

        Type type = new TypeToken<NamiResponse<Collection<NamiTaetigkeitAssignmentListElement>>>() {
        }.getType();
        NamiResponse<Collection<NamiTaetigkeitAssignmentListElement>> resp = con.executeApiRequest(httpGet, type);
        
        if (resp.isSuccess()) {
            return resp.getData();
        } else {
            return null;
        }
    }
}
