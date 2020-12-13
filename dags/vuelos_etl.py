import datetime

import airflow
from airflow.operators import bash_operator
from airflow.contrib.operators.dataflow_operator import DataFlowJavaOperator
from airflow.contrib.operators.gcs_to_bq import GoogleCloudStorageToBigQueryOperator
from airflow.contrib.operators.bigquery_operator import BigQueryOperator


YESTERDAY = datetime.datetime.now() - datetime.timedelta(days=1)

default_args = {
    "owner": "dpalma",
    "depends_on_past": False,
    "email": [""],
    "email_on_failure": False,
    "email_on_retry": False,
    "retries": 1,
    "retry_delay": datetime.timedelta(minutes=5),
    "start_date": YESTERDAY,
    "dataflow_default_options": {
        "project": "diego-palma",
        "stagingLocation": "gs://prueba-option/temp/dataflow/staging/",
        "tempLocation": "gs://prueba-option/temp/",
    },
}

with airflow.DAG(
    "prueba_tecnica",
    "catchup=False",
    default_args=default_args,
    template_searchpath=["/home/airflow/gcs/dags/"],
    schedule_interval=datetime.timedelta(days=1),
) as dag:

    dataflow_op = DataFlowJavaOperator(
        task_id="dataflow_task",
        jar="gs://prueba-option/dataflow-etl/prueba-tecnica-1.jar",
    )

    vuelos_to_bq = GoogleCloudStorageToBigQueryOperator(
        task_id="gcs_vuelos_to_bq",
        bucket="prueba-option",
        source_objects=["output/vuelos/*"],
        destination_project_dataset_table="diego-palma.prueba_option.vuelos",
        source_format="NEWLINE_DELIMITED_JSON",
        write_disposition="WRITE_TRUNCATE",
        dag=dag,
    )

    pasajeros_to_bq = GoogleCloudStorageToBigQueryOperator(
        task_id="gcs_pasajeros_to_bq",
        bucket="prueba-option",
        source_objects=["output/pasajeros/*"],
        destination_project_dataset_table="diego-palma.prueba_option.pasajeros",
        source_format="NEWLINE_DELIMITED_JSON",
        write_disposition="WRITE_TRUNCATE",
        dag=dag,
    )

    ventas_to_bq = GoogleCloudStorageToBigQueryOperator(
        task_id="gcs_ventas_to_bq",
        bucket="prueba-option",
        source_objects=["output/ventas/*"],
        destination_project_dataset_table="diego-palma.prueba_option.ventas",
        source_format="NEWLINE_DELIMITED_JSON",
        write_disposition="WRITE_TRUNCATE",
        dag=dag,
    )

    schema_prod = BigQueryOperator(
        task_id="create_schema_prod",
        sql="/sql/schema_produccion.sql",
        destination_dataset_table="diego-palma.prueba_option.schema_prod",
        write_disposition="WRITE_TRUNCATE",
        use_legacy_sql=False,
    )

    dataflow_op >> [vuelos_to_bq, pasajeros_to_bq, ventas_to_bq] >> schema_prod
