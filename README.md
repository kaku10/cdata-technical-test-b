# Assignment
Cybozu kintoneの顧客管理アプリをRDBMSに複製（レプリケーション）するアプリケーション

kintoneの開発者ライセンスの取得方法を参考ください
https://www.cdata.com/jp/blog/kintone-account
RDBはMySQL、Oracle Express、SQLServer、Postgresなど問いません

A. 差分での追加・変更が出来ること（削除の同期は不要）

B. GUIで実行結果が確認出来ること（想定は処理結果のステータス、処理件数、現在、登録されているRDBMS側とkintone側のデータ）

# Setup
1. Put CData JDBC Driver for Kintone in libs directory. (cf. https://cdn.cdata.com/help/EKJ/jp/jdbc/ )
2. Set environment variables `KINTONE_JDBC_URL` and `SQLITE_JDBC_URL` to the JDBC URL.
