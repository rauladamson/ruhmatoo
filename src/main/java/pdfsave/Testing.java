package pdfsave;

public class Testing {
    public static void main(String[] args) {
        String url1 = "https://ois2.ut.ee/api/courses/LTAT.03.007/versions/fde0bca8-705f-74c9-456e-e68104c23b53";
        String url2 = "https://ois2.ut.ee/api/courses/LTAT.03.003/versions/d2bd00a1-232d-5535-d840-b0b78d26496c";
        String url3 = "https://ois2.ut.ee/#/courses/LTAT.03.003/version/45b7f8eb-7e9e-cbde-6187-877820687815/details";
        // Kiire häkk: ,et teha Urlist API urli, # -> api, version -> versions, õppeaine versioonist -/details.
        FetchData a = new FetchData();
        String[] urls = {url1, url2, url3};
        String vastus = a.main(urls);
    }
}
