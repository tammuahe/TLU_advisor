from optimum.exporters.onnx import main_export
from optimum.exporters.onnx.model_configs import BertOnnxConfig
from transformers import AutoConfig
from transformers import AutoTokenizer

MODEL_ID = "dangvantuan/vietnamese-document-embedding"
OUTPUT_DIR = "onnx_output"

# Save tokenizer
tokenizer = AutoTokenizer.from_pretrained(MODEL_ID, trust_remote_code=True)
tokenizer.save_pretrained(OUTPUT_DIR)

# Load config and create custom ONNX config if needed
config = AutoConfig.from_pretrained(MODEL_ID, trust_remote_code=True)

# Export to ONNX using main_export
main_export(
    MODEL_ID,
    output=OUTPUT_DIR,
    trust_remote_code=True,
    # Add model_kwargs if you need custom outputs
    # model_kwargs={"output_attentions": True, "output_hidden_states": True},
)

print(f"Model and tokenizer saved to {OUTPUT_DIR}")