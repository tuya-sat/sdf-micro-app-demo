import { PageHeader, Spin } from "antd";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import api from "src/api";
import HelloTemplate from "../components/HelloTemplate";
import TableList from "../components/Table";
import styled from "./index.module.less";

interface ISaaSResult {
  asset_full_name: string;
  asset_id: string;
  asset_name: string;
  child_asset_count: string;
  child_device_count: string;
  is_authorized: string;
  sub_assets: string;
}

const Page = () => {
  const { t } = useTranslation();
  const [appid, setAppid] = useState<string>("-1");

  const [datas, setDatas] = useState<ISaaSResult[]>([]);
  const [spinLoading, setspinLoading] = useState(true);
  function getList() {
    api
      .get(`/custom-api/v1.0/assets/-1`)
      .then((res) => {
        setDatas(res.result);
      })
      .catch((e) => console.log(e))
      .finally(() => {
        setspinLoading(false);
      });
  }
  useEffect(() => {
    getList();
  }, []);
  return (
    <div>
      <PageHeader
        className={styled["site-page-header"]}
        onBack={() => null}
        backIcon={false}
        title={t("title")}
        ghost={false}
      />
      <div className={styled["site-page-body"]}>
        <HelloTemplate
          setAppid={setAppid}
          getList={getList}
          setspinLoading={setspinLoading}
        />
        <Spin spinning={spinLoading} style={{ height: "100%" }}>
          <TableList
            datas={datas}
            getList={getList}
            setspinLoading={setspinLoading}
          />
        </Spin>
      </div>
    </div>
  );
};

export default Page;
