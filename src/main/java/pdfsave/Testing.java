package pdfsave;

public class Testing {
    public static void main(String[] args) {
        String url1 = "https://ois2.ut.ee/api/courses/LTAT.03.007/versions/fde0bca8-705f-74c9-456e-e68104c23b53";
        String url2 = "https://ois2.ut.ee/api/courses/LTAT.03.003/versions/d2bd00a1-232d-5535-d840-b0b78d26496c";
        // Kiire häkk: ,et teha Urlist API urli, # -> api, version -> versions, õppeaine versioonist -/details.
        FetchData a = new FetchData();
        String[] urls = {url1, url2};
        FetchData.main(urls);
    }
}
